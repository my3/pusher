package pusher

class Device {
	
	static hasMany = {apps: Application}
	String type
	String description
	
    static constraints = {
		type size:3..100, blank:false, nullable:true, unique:true
		description size:3..500, blank:false
    }
}
