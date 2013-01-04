package pusher

class User {
	String login
	String password
	String email
	String phone
    static constraints = {
		login size:3..50, blank: false, nullable: false
		password size:3..50, blank: false, nullable: false
		email size:3..150, blank: false, nullable: false
		phone size:3..30, blank: false, nullable: false		
    }
}
