package excel;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SortObj implements Comparable<SortObj> {
	
	public SortObj() { }
	
	public SortObj(String json) {
    	JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject sortObject = reader.readObject();
        reader.close();
        this.priority = sortObject.getInt("priority");
        this.column = sortObject.getString("column");
        this.direction = sortObject.getString("direction");
    }
	
	@XmlElement(name="priority")
	public int priority;
	@XmlElement(name="column")
	public String column;
	@XmlElement(name="direction")
	public String direction;
	
	@Override
	public String toString() {
		return "priority=" + priority + ";column=" + column + ";direction=" + direction;
	}

	@Override
	public int compareTo(SortObj o) {
		if(this.priority < o.priority)
			return -1;
		if(this.priority > o.priority)
			return 1;
		else
			return 0;
	}
}
