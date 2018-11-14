package com.translatmaster.utils;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

/**
 * 分组工具，根据key将list中数据分组
 */
public class SortListUtil {
    /**
     * 通过HashMap键值对的特性，将ArrayList的数据进行分组，返回带有分组Header的ArrayList。
     *
     * @param details 从后台接受到的ArrayList的数据，其中日期格式为：yyyy-MM-dd HH:mm:ss
     * @return list  返回的list是分类后的包含header（yyyy-MM-dd）和item（HH:mm:ss）的ArrayList
     */
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public static ArrayList<PinnedSectionBean> getData(List<WarnDetail> details){
//        // 最后我们要返回带有分组的list,初始化
//        ArrayList<PinnedSectionBean> list = new ArrayList<PinnedSectionBean>();
//        // WarnDetail作为key是yyyy-MM-dd格式,List<WarnDetail>是对应的值是HH:mm:ss格式
//        Map<WarnDetail, List<WarnDetail>> map = new HashMap<WarnDetail, List<WarnDetail>>();
//        // 按照warndetail里面的时间进行分类
//        WarnDetail detail = new WarnDetail();
//        for (int i = 0; i < details.size(); i++) {
//            try {
//                String key = DateFormatUtil.exchangeStringDate(details.get(i).getAddtime()) ;
//                if (detail.getAddtime() != null && !"".equals(detail.getAddtime())) {
//                    // 判断这个Key对象有没有生成,保证是唯一对象.如果第一次没有生成,那么new一个对象,之后同组的其他item都指向这个key
//                    boolean b = !key.equals(detail.getAddtime().toString());
//                    if (b) {
//                        detail = new WarnDetail();
//                    }
//                }
//                detail.setAddtime(key);
//                // 把属于当天yyyy-MM-dd的时间HH:mm:ss全部指向这个key
//                List<WarnDetail> warnDetails = map.get(detail);
//                // 判断这个key对应的值有没有初始化,若第一次进来,这new一个arryalist对象,之后属于这一天的item都加到这个集合里面
//                if (warnDetails == null) {
//                    warnDetails = new ArrayList<WarnDetail>();
//                }
//                String time = details.get(i).getAddtime();
//                time = DateFormatUtil.exchangeStringTime(time);
//                details.get(i).setAddtime(time);
//                warnDetails.add(details.get(i));
//
//                map.put(detail, warnDetails);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        // 用迭代器遍历map添加到list里面
//        Iterator iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry entry = (Map.Entry) iterator.next();
//            WarnDetail key = (WarnDetail) entry.getKey();
//            //我们的key(yyyy-MM-dd)作为标题.类别属于SECTION
//            list.add(new PinnedSectionBean(SECTION, key));
//            List<WarnDetail> li = (List<WarnDetail>) entry.getValue();
//            for (WarnDetail warnDetail : li) {
//                //对应的值(HH:mm:ss)作为标题下的item,类别属于ITEM
//                list.add(new PinnedSectionBean(ITEM, warnDetail));
//            }
//        }

        // 把分好类的hashmap添加到list里面便于显示
//        return list;
//    }

}
