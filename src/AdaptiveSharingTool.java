import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AdaptiveSharingTool {

	public static void main(String[] args) {

		BufferedReader br = null;

		try {

			String sCurrentLine;

			//read log file
			br = new BufferedReader(new FileReader("config.txt"));
			
			//create the main user
			sCurrentLine = br.readLine();
			String[] parts = sCurrentLine.split(" ");
			User Alice = new User(parts[0], Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
			
			//add the user's friends
			while ((sCurrentLine = br.readLine()) != null) {
				String[] friends = sCurrentLine.split(" ");
				Alice.addFriend(friends[0], Float.parseFloat(friends[1]));
			}
			
//			Friend bob = user.getFriend("Bob");
//			System.out.println(bob.getName());
//			System.out.println(bob.getTrustLevel());
			
			//read config file
			br.close();
			br = new BufferedReader(new FileReader("log.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				String[] inputs = sCurrentLine.split(" ");
				if (inputs.length == 4){
					//user is sharing something
					Alice.shareToAll();
				}
				else if (inputs.length == 3){
					if(inputs[2].equals("share")){
						Alice.getFriend(inputs[0]).incPostsShared();
					}
					else if (inputs[2].equals("like")){
						Alice.getFriend(inputs[0]).incPostsLiked();
					}
				}
				else if (inputs.length == 1){
					//break so we can continue with our evaluation
					break;
				}
				
			}
			
			// init some paremeters
			float benVsRiskWeight = Alice.getBenVsRiskWeight();
			float seenVsLikeWeight = Alice.getSeenVsLikeWeight();
			
			///now you start presenting results
			while ((sCurrentLine = br.readLine()) != null) {
				String[] inputs = sCurrentLine.split(" ");
				if (inputs.length == 4){
					//user is sharing something
					//for every Friend calculate the decision whether to share or not
					for ( Friend friend : Alice.getFriends()){
						
						//benefit of sharing
						float messagesLiked = friend.getPostsLiked()/friend.getPostsSeen();
						float messagesLikedNormalization = messagesLiked / Alice.getMessagesLikedNormalization();
						float benefitOfSharing = seenVsLikeWeight + (1 - seenVsLikeWeight) * messagesLikedNormalization;
						
						//risk of sharing
						//this is to calculate log in base 2 !!check that again pls
						float k = Float.parseFloat(inputs[2]);
						float t = friend.getTrustLevel();
						float resharingPropability = friend.getPostsShared() / friend.getPostsSeen();
						
						float entropy = (float) (Math.log(k) / Math.log(2));
						
						float partOne = (float) ((k - (k - 1) * friend.getTrustLevel() * ( 1 - resharingPropability) / k) *
								(Math.log(k / k - ( k - 1) * t * (1 - resharingPropability)) / Math.log(2)) );
						
						float one = (k - (k - 1) * friend.getTrustLevel() * ( 1 - resharingPropability) / k);
						float three = k / k - ( k - 1) * t * (1 - resharingPropability);
						float two = (float) ( Math.log(three) / Math.log(2)) ;
						
						float partTwo = (float) (((k - 1) * friend.getTrustLevel() * ( 1 - resharingPropability) / k) *
								(Math.log(k /  t * (1 - resharingPropability)) / Math.log(2)) );
						float entropyWithProtecting = partOne + partTwo;
				
						float informationLeakage = 1 - entropyWithProtecting / entropy;
						
						float riskFromSharing = -1 * informationLeakage * Float.parseFloat(inputs[3]);
						
						float utility = (1 - benVsRiskWeight) * benefitOfSharing + benVsRiskWeight * riskFromSharing;
						System.out.println(utility);
						
					}
					
					
					
					Alice.shareToAll();
				}
				else if (inputs.length == 3){
					if(inputs[2].equals("share")){
						Alice.getFriend(inputs[0]).incPostsShared();
					}
					else if (inputs[2].equals("like")){
						Alice.getFriend(inputs[0]).incPostsLiked();
					}
				}
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	

}
