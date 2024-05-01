package snpc.generate.entity.hibernate.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import snpc.generate.entity.hibernate.GenerateMain;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OptionalConfig {
    private boolean haveTaskDto = false;
    private boolean haveTaskDslCustom = false;
    private boolean haveTaskRelationShip = false;

    public OptionalConfig(List<String> taskExports) {
        if(taskExports.contains(GenerateMain.TASK_I_REPO_CUSTOM_DSL) || taskExports.contains(GenerateMain.TASK_IMPL_REPO_CUSTOM_DSL)) {
            this.haveTaskDslCustom = true;
        }
        if(taskExports.contains(GenerateMain.TASK_ENTITY_RELATIONSHIP)) {
            this.haveTaskRelationShip = true;
        }
        if(taskExports.contains(GenerateMain.TASK_DTO)) {
            this.haveTaskDto = true;
        }
    }
}
