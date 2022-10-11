import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//ʹ�÷�����������
//�������ݼ�������
public class CircularParser {
    private final List<Activity> originList = new ArrayList<>();
    private final List<String> circularList = new ArrayList<>();
    private final List<String> appearedList = new ArrayList<>(); //ȥ���ظ�������ǵ�originList
    private final List<Activity> markedList = new ArrayList<>();
    private int min = 9999, max;   //ԭʼ�б���ѭ��Ԫ���±����Сֵ�����ֵ
    private Activity fakeCircularActivity = new Activity("");  //��©��ѭ��Ԫ�� TIPS:��һ��ֻ����һ����©Ԫ�ص���������ܼ�����һ��ѭ��Ԫ�ص����

    //�����ַ���������ԭʼ�б�
    public List<Activity> splitProcess(String originProcess, String delimeter){
        Arrays.stream(originProcess.split(delimeter))
                .forEach(activityName -> originList.add(new Activity(activityName))); // �ָ��ַ���

        return originList;
    }

    //���ɴ�����ı�ǹ����б�
    public List<Activity> HandleCircular(List<Activity> originList){
        //ѭ���б��м����ظ���������б��м���
        for (Activity activity : originList) {
            if(appearedList.contains(activity.getName())) {
                if(!circularList.contains(activity.getName())){
                    circularList.add(activity.getName());    //ѭ���б��м�������
                }
                originList.stream()
                        .filter(activity1 -> activity1.getName().equals(activity.getName()))
                        .forEach(activity1 -> activity1.setCircular(true));
                activity.setCircular(true); //��ԭʼ�б��б��ѭ��Ԫ��
            }
            else
                appearedList.add(activity.getName());
        }

        //Ѱ��ԭʼ�б��ѭ��Ԫ�ص��±귶Χ
        for (Activity activity : originList) {
            if(activity.isCircular()){
                if(min > originList.indexOf(activity))
                    min = originList.indexOf(activity);
                if(max <=originList.indexOf(activity))
                    max = originList.indexOf(activity);
            }
        }
        //Ѱ����©��ѭ��Ԫ��
        for (Activity activity : originList) {
            if(!activity.isCircular()){
                if(min < originList.indexOf(activity) && max > originList.indexOf(activity)) {
                    activity.setFakeCircular(true);
                    fakeCircularActivity = activity;
                }
            }
        }

        //����ѭ���б���appearedList������Щactivity������ѭ��, �Լ�©��֮��
        for (String activityName : appearedList) {
            if(circularList.contains(activityName)){
                markedList.add(new Activity(activityName, true, false));
            } else if (activityName.equals(fakeCircularActivity.getName())) {
                markedList.add(fakeCircularActivity);
            } else{
                markedList.add(new Activity(activityName));
            }
        }

        return markedList;  //�������Щ��ѭ��Ԫ�أ���Щ����©��ѭ��Ԫ��
    }

    //�������յ��ַ���
    public String stringModel2String(List<Activity> markedList){
        StringBuilder markedStringBuffer = new StringBuilder();   //�����ת��string
        String markedString;

        if(!markedList.contains(fakeCircularActivity)){
            //�����Ƿ���ѭ��Ԫ�ء��Ƿ���ѭ��Ԫ�ص��׺�β��������Ӧ�����
            for (Activity activity : markedList) {
                if(circularList.contains(activity.getName())){
                    if(circularList.indexOf(activity.getName()) == 0){
                        markedStringBuffer.append("[");
                        markedStringBuffer.append(activity.getName());
                    } else if (circularList.indexOf(activity.getName()) == circularList.size()-1) {
                        markedStringBuffer.append("(");
                        markedStringBuffer.append(activity.getName());
                        markedStringBuffer.append(")");
                        markedStringBuffer.append("]");
                    }else{
                        markedStringBuffer.append(activity.getName());
                    }
                }else{
                    markedStringBuffer.append(activity.getName());
                }

                if(markedList.indexOf(activity) != markedList.size() - 1){
                    markedStringBuffer.append("-->");
                }
            }
        }else {
            //����˳��
            int indexOfFakeCircularActivity = markedList.indexOf(fakeCircularActivity);
            int indexOfFirstCircularActivity = min;
            markedList.add(indexOfFirstCircularActivity, fakeCircularActivity);
            markedList.remove(indexOfFakeCircularActivity + 1);
            circularList.add(fakeCircularActivity.getName());

            //�����Ƿ���ѭ��Ԫ�ء��Ƿ���ѭ��Ԫ�ص��׺�β��������Ӧ�����
            for (Activity activity : markedList) {
                if(circularList.contains(activity.getName())){
                    if(circularList.indexOf(activity.getName()) == circularList.size()-2){
                        markedStringBuffer.append(activity.getName());
                        markedStringBuffer.append("]");
                    } else if (circularList.indexOf(activity.getName()) == circularList.size()-1) {
                        markedStringBuffer.append("[");
                        markedStringBuffer.append("(");
                        markedStringBuffer.append(activity.getName());
                        markedStringBuffer.append(")");
                    }else{
                        markedStringBuffer.append(activity.getName());
                    }
                }else{
                    markedStringBuffer.append(activity.getName());
                }

                if(markedList.indexOf(activity) != markedList.size() - 1){
                    markedStringBuffer.append("-->");
                }
            }
        }

        markedString = markedStringBuffer.toString();
        return markedString;
    }

}
