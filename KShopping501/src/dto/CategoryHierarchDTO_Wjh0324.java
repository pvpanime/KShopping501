package dto;

public class CategoryHierarchDTO_Wjh0324 {
	public final String name;
	public final CategoryHierarchDTO_Wjh0324 parent;
	public CategoryHierarchDTO_Wjh0324(String name, CategoryHierarchDTO_Wjh0324 parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public String getFlattenCategory() {
		if (parent == null) return this.name;
		return parent.getFlattenCategory() + " > " + this.name;
	}
	
	public String toString() {
		return String.format("<Category '%s'%s>", name, parent == null ? "" : " (has parent)");
	}
}
