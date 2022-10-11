import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//使用方法见测试类
//测试数据见测试类
public class CircularParser {
    private final List<Activity> originList = new ArrayList<>();
    private final List<String> circularList = new ArrayList<>();
    private final List<String> appearedList = new ArrayList<>(); //去除重复，带标记的originList
    private final List<Activity> markedList = new ArrayList<>();
    private int min = 9999, max;   //原始列表中循环元素下标的最小值、最大值
    private Activity fakeCircularActivity = new Activity("");  //遗漏的循环元素 TIPS:这一版只做了一个遗漏元素的情况，不能检测大于一个循环元素的情况

    //分离字符串，生成原始列表
    public List<Activity> splitProcess(String originProcess, String delimeter){
        Arrays.stream(originProcess.split(delimeter))
                .forEach(activityName -> originList.add(new Activity(activityName))); // 分割字符串

        return originList;
    }

    //生成待输出的标记过的列表
    public List<Activity> HandleCircular(List<Activity> originList){
        //循环列表中加入重复活动，出现列表中加入活动
        for (Activity activity : originList) {
            if(appearedList.contains(activity.getName())) {
                if(!circularList.contains(activity.getName())){
                    circularList.add(activity.getName());    //循环列表中加入这个活动
                }
                originList.stream()
                        .filter(activity1 -> activity1.getName().equals(activity.getName()))
                        .forEach(activity1 -> activity1.setCircular(true));
                activity.setCircular(true); //在原始列表中标记循环元素
            }
            else
                appearedList.add(activity.getName());
        }

        //寻找原始列表的循环元素的下标范围
        for (Activity activity : originList) {
            if(activity.isCircular()){
                if(min > originList.indexOf(activity))
                    min = originList.indexOf(activity);
                if(max <=originList.indexOf(activity))
                    max = originList.indexOf(activity);
            }
        }
        //寻找遗漏的循环元素
        for (Activity activity : originList) {
            if(!activity.isCircular()){
                if(min < originList.indexOf(activity) && max > originList.indexOf(activity)) {
                    activity.setFakeCircular(true);
                    fakeCircularActivity = activity;
                }
            }
        }

        //根据循环列表标记appearedList中有哪些activity出现了循环, 以及漏网之鱼
        for (String activityName : appearedList) {
            if(circularList.contains(activityName)){
                markedList.add(new Activity(activityName, true, false));
            } else if (activityName.equals(fakeCircularActivity.getName())) {
                markedList.add(fakeCircularActivity);
            } else{
                markedList.add(new Activity(activityName));
            }
        }

        return markedList;  //标记了哪些是循环元素，哪些是遗漏的循环元素
    }

    //生成最终的字符串
    public String stringModel2String(List<Activity> markedList){
        StringBuilder markedStringBuffer = new StringBuilder();   //最后再转成string
        String markedString;

        if(!markedList.contains(fakeCircularActivity)){
            //根据是否是循环元素、是否是循环元素的首和尾，做出对应的输出
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
            //调换顺序
            int indexOfFakeCircularActivity = markedList.indexOf(fakeCircularActivity);
            int indexOfFirstCircularActivity = min;
            markedList.add(indexOfFirstCircularActivity, fakeCircularActivity);
            markedList.remove(indexOfFakeCircularActivity + 1);
            circularList.add(fakeCircularActivity.getName());

            //根据是否是循环元素、是否是循环元素的首和尾，做出对应的输出
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
