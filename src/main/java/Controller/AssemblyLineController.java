package Controller;

import java.util.List;
import java.util.stream.Collectors;

import Domain.AssemblyLine;

public class AssemblyLineController {
	
	private AssemblyLine assemblyLine;
	
	public AssemblyLineController(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
	}
	
	public List<Integer> giveAllWorkPosts() {		
		return assemblyLine.getWorkPosts()
				.stream()
				.map(workPost -> workPost.getId())
				.collect(Collectors.toList());
	}
} 
