import java.util.List;

//ʹ�÷�����������
//�������ݼ�������
public class MineCircularRelationship {
    public String showVariant(String originProcess){
        CircularParser circularParser = new CircularParser();
        List<Activity> originList = circularParser.splitProcess(originProcess, "-->");  //�ָ��ַ���������originList
        List<Activity> markedList = circularParser.HandleCircular(originList);  //���ɱ�Ǻõ�markedList
        String variant_String = circularParser.stringModel2String(markedList); //�������յ����̱����ַ���

        return variant_String;
    }
}
