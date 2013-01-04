package pusher

import com.sun.istack.internal.Nullable;


class Application {

	String code
	String name
	String description
	
    static constraints = {
		code size:3..8, blank: false, nullable: false, unique: true 
		name size:3..50, blank: false, nullable: false
		description size:0..500, blank: true
    }
}
