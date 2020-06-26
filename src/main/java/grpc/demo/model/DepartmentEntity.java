package grpc.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

@Data
@With
@RequiredArgsConstructor
@AllArgsConstructor
@CompoundIndexes({@CompoundIndex(name = "guid_name_dept", def = "{'guid':1, 'name':1}", unique = true)})
public class DepartmentEntity {
    @Id
    protected String guid;
    protected String name;
    protected String description;
}
