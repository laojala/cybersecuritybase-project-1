package sec.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
//@Table(name = "Signup")
public class Signup extends AbstractPersistable<Long> {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
    private Long id;
//    @Column(name = "name")
    private String attendee;
//    @Column(name = "message")
    private String message;

    public Signup() {
        super();
    }

    public Signup(String name, String message, Long id) {
        this();
        this.attendee = name;
        this.message = message;
        this.id = id;
    }
    
//    public Signup(String name, String message) {
//        this();
//        this.attendee = name;
//        this.message = message;
//    }

    public Long getId() {
        return id;
    }
    
    public String getAttendee() {
        return attendee;
    }

    public void setAttendee(String name) {
        this.attendee = attendee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
