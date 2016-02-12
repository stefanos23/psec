public class Friend {

	private String name;
	private float trustLevel;
	private float postsSeen, postsLiked, postsShared;

	public Friend(String name, float trustLevel) {
		this.name = name;
		this.trustLevel = trustLevel;
		postsSeen = postsLiked = postsShared = 0;
	}

	public String getName(){
		return name;
	}
	
	public float getTrustLevel(){
		return trustLevel;
	}

	public float getPostsSeen() {
		return postsSeen;
	}

	public void incPostsSeen() {
		this.postsSeen++;
	}

	public float getPostsLiked() {
		return postsLiked;
	}

	public void incPostsLiked() {
		this.postsLiked++;
	}

	public float getPostsShared() {
		return postsShared;
	}

	public void incPostsShared() {
		this.postsShared++;
	}
	


}
