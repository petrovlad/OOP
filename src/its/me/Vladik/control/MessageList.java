package its.me.Vladik.control;

import javafx.scene.control.Alert;

import java.util.ArrayList;

public class MessageList {
    ArrayList<Message> messages;

    public MessageList() {
        messages = new ArrayList<Message>();
    }

    public void add(Message message) {
        messages.add(message);
    }

    public void deleteAll() {
        messages.clear();
    }

    public String formReport() {
        String report = new String();
        String errors = new String();
        String warnings = new String();
        if (!messages.isEmpty()) {
            for (Message message : messages) {
                if (message.type == Message.Type.WARNING) {
                   switch (message.code) {
                       case (1) :
                           warnings = warnings.concat("WARNING: Non-existable field '" + message.causeBy + "'" + " on line " + String.valueOf(message.line) + "\n");
                           break;
                       case (2) :
                           warnings = warnings.concat("WARNING: Number of quotes is not odd for '" + message.causeBy + "'"  + " on line " + String.valueOf(message.line) + "\n");
                           break;
                       case (3) :
                           warnings = warnings.concat("WARNING: Point1 > Point2 '" + message.causeBy + "'"  + " on line " + String.valueOf(message.line) + "\n");
                           break;
                       case (4) :
                           errors = errors.concat("WARNING: Field value have not been found for '" + message.causeBy + "'"  + " on line " + String.valueOf(message.line) + "\n");
                           break;
                   }
                }
                else {
                    switch (message.code) {
                        case (1) :
                            errors = errors.concat("ERROR: Non-existable figure '" + message.causeBy + "'"  + " on line " + String.valueOf(message.line) + "\n");
                            break;
                        case (2) :
                            errors = errors.concat("ERROR: Invalid field value '" + message.causeBy + "'"  + " on line " + String.valueOf(message.line) + "\n");
                            break;
                        case (3) :
                            errors = errors.concat("ERROR: Not all points have been initialized for '" + message.causeBy + "'"  + " on line " + String.valueOf(message.line) + "\n");
                            break;

                    }
                }
            }
        }
        report = report.concat(errors);
        report = report.concat(warnings);
        return report;
    }

}
