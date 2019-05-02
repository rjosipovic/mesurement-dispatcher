package home.mesurement.dispatcher.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application", ignoreInvalidFields = false)
public class ApplicationConfig {
	
	private Collector collector = new Collector();
	private List<Mesurement> mesurements = new ArrayList<>();
	
	public Collector getCollector() {
		return collector;
	}
	
	public List<Mesurement> getMesurements() {
		return mesurements;
	}
	
	public static class Collector {
		private String endpoint;
		
		public String getEndpoint() {
			return endpoint;
		}
		
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
	}
	
	public static class Mesurement {

		private String name;
		private String script;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getScript() {
			return script;
		}
		public void setScript(String script) {
			this.script = script;
		}
		@Override
		public int hashCode() {
			final int prime = 71;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Mesurement other = (Mesurement) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Mesurement [name=" + name + ", script=" + script + "]";
		}
	}
}
