import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestShowVariant {
    @Test
    public void doTestShowVariant(){
        //生成测试数据
        List<String> originProcesses = new ArrayList<>();
        originProcesses.add("Apply Repair-->User Identification-->Intelligent Response-->Manual Interaction-->Release Order-->Order Assignment-->Repair-->Test Repair-->Restart Repair-->Repair-->Test Repair-->Restart Repair-->Repair-->Test Repair-->Success-->Service Evaluation-->Archive");
        originProcesses.add("Apply Repair-->User Identification-->Intelligent Response-->Manual Interaction-->Release Order-->Order Assignment-->Repair-->Test Repair-->Success-->Service Evaluation-->Archive");
        originProcesses.add("Apply Repair-->User Identification-->Intelligent Response-->Manual Interaction-->Service Evaluation-->Archive");
        originProcesses.add("Apply Repair-->User Identification-->Intelligent Response-->Manual Interaction-->Release Order-->Order Assignment-->Repair-->Test Repair-->Restart Repair-->Repair-->Test Repair-->Success-->Service Evaluation-->Archive");
        originProcesses.add("Apply Repair-->User Identification-->Intelligent Response-->Manual Interaction-->Release Order-->Order Assignment-->Repair-->Test Repair-->Restart Repair-->Repair-->Test Repair-->Restart Repair-->Repair-->Test Repair-->Renew-->Service Evaluation-->Archive");

        //执行功能
        MineCircularRelationship mineCircularRelationship = new MineCircularRelationship();
        for (String originProcess:
                originProcesses) {
            String variant_String = mineCircularRelationship.showVariant(originProcess);
            System.out.println(variant_String);
        }


    }
}
