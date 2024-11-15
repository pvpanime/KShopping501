package dto;

public class CategoryDTO {

    private Integer categoryId;  // 카테고리 ID
    private String name;         // 카테고리 이름
    private Integer parentId;    // 상위 카테고리 ID (없을 경우 null)

    // 기본 생성자
    public CategoryDTO() {
    }

    // 매개변수가 있는 생성자
    public CategoryDTO(Integer categoryId, String name, Integer parentId) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
    }

    // Getter 및 Setter
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}


