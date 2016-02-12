import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class User {

	private String name;
	private float benVsRiskWeight;
	private float seenVsLikeWeight;
	private List<Friend> friends = new ArrayList<Friend>();
	private Dictionary<String, Friend> map = new Hashtable<>();
//	map.put("dog", "type of animal");
//	System.out.println(map.get("dog"));
	
	
	public User(String name, float benVsRiskWeight, float seenVsLikeWeight){
		this.name = name;
		this.benVsRiskWeight = benVsRiskWeight;
		this.seenVsLikeWeight = seenVsLikeWeight;
	}
	
	//add a friend with a specific trustLevel
	public void addFriend(String name, float trustLevel){
		
		Friend friend = new Friend(name, trustLevel);
		friends.add(friend);
		map.put(name,friend);
		
	}
	
	public Friend getFriend(String name){
		return map.get(name);
	}
	
	public List<Friend> getFriends(){
		return friends;
	}
	
	
	public void shareToAll(){
		for(Friend friend : friends){
			friend.incPostsSeen();
		}
	}

	public float getBenVsRiskWeight() {
		return benVsRiskWeight;
	}

	public float getSeenVsLikeWeight() {
		return seenVsLikeWeight;
	}
	
	public String getName() {
		return name;
	}
	
public float getMessagesLikedNormalization(){
		
		float max = 0;
		for ( Friend friend : friends){
			float messagesLiked = friend.getPostsLiked()/friend.getPostsSeen();
			if (messagesLiked > max) max = messagesLiked;
		}
		
		return max;
	}
}
