package resourses;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


public class ParserText {
	
	public Map<Object,Object> parsToStrStr(List<String> list){
		for(String line : list){
			line.trim();
			if (line.indexOf("=")==-1){
				new Exception("ParserText:parsToStrStr - no sign \"=\" in string ");
			};
			
			
			
		}
		
		return null;
	}

}
