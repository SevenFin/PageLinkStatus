package com.status.service.impl;

import com.status.dao.IJournalDao;
import com.status.model.QueryObj;
import com.status.model.RecallObj;
import com.status.model.ResultSingle;
import com.status.model.ResultVo;
import com.status.service.IJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;


@Service
public class JournalService implements IJournalService {

    @Autowired
    private IJournalDao journalDAO;

    public List<ResultVo> queryByYear(QueryObj queryObj, RecallObj recallObj) throws Exception {

        long start = System.currentTimeMillis();

        char firstChar = queryObj.getVendor().toLowerCase().charAt(0);

        QueryThread thread = null;
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 5,
                TimeUnit.MINUTES, queue);

        Map<Key, Integer> map = new Hashtable<Key, Integer>();

        int beginYear = Integer.parseInt(queryObj.getBeginYear());
        int endYear = Integer.parseInt(queryObj.getEndYear());
        // c、w开头的表需要添加年的分表名

        List<String> yearList = new ArrayList<String>();
        yearList.add("");
        if ((firstChar == 'c' || firstChar == 'w') && queryObj.getType() != 10) {
            yearList = new ArrayList<String>();
            if (beginYear < 2000) {
                yearList.add("");
                yearList.add(2005 + "_");
            } else if (beginYear >= 2000 && beginYear <= 2005) {
                yearList.add(2005 + "_");
            }
            if (endYear >= 2006) {
                int iterStart = Math.max(2006, beginYear);
                for (int i = iterStart; i <= endYear; i++) {
                    yearList.add(i + "_");
                }
            }
        }

        // 计数
        CountDownLatch threadSignal = new CountDownLatch(16 * yearList.size());
        for (int i = 0x0; i <= 0xf; i++) {
            for (String year : yearList) {

                String schemaName = "page_links_" + year + firstChar
                        + String.format("%01x", i);
                thread = new QueryThread(schemaName, map, threadSignal, journalDAO, queryObj);
                executor.execute(thread);
            }
        }

        threadSignal.await();// 所有线程运行完毕
        executor.shutdown();

        // 查询完毕 处理map

        List<ResultVo> resultList = new ArrayList<ResultVo>();
        ResultVo resultVo = null;
        if (queryObj.getType() == 10) {
            resultVo = new ResultVo();
            resultVo.setYear("jinfo");
            String countState0 = map.get(new Key(0, -1)) + "";
            String countState1 = map.get(new Key(1, -1)) + "";
            String countState2 = map.get(new Key(2, -1)) + "";
            String countState3 = map.get(new Key(3, -1)) + "";
            String countState6 = map.get(new Key(6, -1)) + "";
            String countState7 = map.get(new Key(7, -1)) + "";
            String countState8 = map.get(new Key(8, -1)) + "";
            String countState9 = map.get(new Key(9, -1)) + "";
            resultVo.setCountState0(countState0.equals("null") ? "0" : countState0);
            resultVo.setCountState1(countState1.equals("null") ? "0" : countState1);
            resultVo.setCountState2(countState2.equals("null") ? "0" : countState2);
            resultVo.setCountState3(countState3.equals("null") ? "0" : countState3);
            resultVo.setCountState6(countState6.equals("null") ? "0" : countState6);
            resultVo.setCountState7(countState7.equals("null") ? "0" : countState7);
            resultVo.setCountState8(countState8.equals("null") ? "0" : countState8);
            resultVo.setCountState9(countState9.equals("null") ? "0" : countState9);
            resultVo.addCount();
            resultList.add(resultVo);
        } else {
            for (int year = endYear; year >= beginYear; year--) {
                resultVo = new ResultVo();
                resultVo.setYear(year + "");
                String countState0 = map.get(new Key(0, year)) + "";
                String countState1 = map.get(new Key(1, year)) + "";
                String countState2 = map.get(new Key(2, year)) + "";
                String countState3 = map.get(new Key(3, year)) + "";
                String countState6 = map.get(new Key(6, year)) + "";
                String countState7 = map.get(new Key(7, year)) + "";
                String countState8 = map.get(new Key(8, year)) + "";
                String countState9 = map.get(new Key(9, year)) + "";
                resultVo.setCountState0(countState0.equals("null") ? "0" : countState0);
                resultVo.setCountState1(countState1.equals("null") ? "0" : countState1);
                resultVo.setCountState2(countState2.equals("null") ? "0" : countState2);
                resultVo.setCountState3(countState3.equals("null") ? "0" : countState3);
                resultVo.setCountState6(countState6.equals("null") ? "0" : countState6);
                resultVo.setCountState7(countState7.equals("null") ? "0" : countState7);
                resultVo.setCountState8(countState8.equals("null") ? "0" : countState8);
                resultVo.setCountState9(countState9.equals("null") ? "0" : countState9);
                resultVo.addCount();
                resultList.add(resultVo);
            }
        }

        long end = System.currentTimeMillis();
        double span = (end - start) / 1000.0;
        if (recallObj.getTimeSpan() != null) {
            span += recallObj.getTimeSpan();
        }
        recallObj.setTimeSpan(span);

        return resultList;
    }

    public int updateState(QueryObj queryObj, RecallObj recallObj) throws Exception {

        long start = System.currentTimeMillis();

        //========================================================================

        char firstChar = queryObj.getVendor().toLowerCase().charAt(0);
        UpdateThread thread = null;
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 5,
                TimeUnit.MINUTES, queue);

        int beginYear = Integer.parseInt(queryObj.getBeginYear());
        int endYear = Integer.parseInt(queryObj.getEndYear());

        // c、w开头的表需要添加年的分表名
        List<String> yearList = new ArrayList<String>();
        yearList.add("");
        if ((firstChar == 'c' || firstChar == 'w') && queryObj.getType() != 10) {
            yearList = new ArrayList<String>();
            if (beginYear < 2000) {
                yearList.add("");
                yearList.add(2005 + "_");
            } else if (beginYear >= 2000 && beginYear <= 2005) {
                yearList.add(2005 + "_");
            }
            if (endYear >= 2006) {
                int iterStart = Math.max(2006, beginYear);
                for (int i = iterStart; i <= endYear; i++) {
                    yearList.add(i + "_");
                }
            }
        }


        // 计数
        CountDownLatch threadSignal = new CountDownLatch(16 * yearList.size());
        for (int i = 0x0; i <= 0xf; i++) {
            for (String year : yearList) {

                String schemaName = "page_links_" + year + firstChar
                        + String.format("%01x", i);
                thread = new UpdateThread(schemaName, threadSignal, journalDAO, queryObj);
                executor.execute(thread);
            }
        }

        threadSignal.await();// 所有线程运行完毕
        executor.shutdown();

        //========================================================================

        long end = System.currentTimeMillis();
        double span = (end - start) / 1000.0;
        if (recallObj.getTimeSpan() != null) {
            span += recallObj.getTimeSpan();
        }
        recallObj.setTimeSpan(span);
        return 0;//update条数
    }
}

class QueryThread extends Thread {
    private String schemaName;
    private Map<Key, Integer> countMap;
    private CountDownLatch threadSignal;
    private IJournalDao journalDAO;
    private QueryObj queryObj;

    public QueryThread(String schemaName, Map<Key, Integer> countMap,
                       CountDownLatch threadSignal, IJournalDao journalDAO,
                       QueryObj queryObj) {
        this.schemaName = schemaName;
        this.countMap = countMap;
        this.threadSignal = threadSignal;
        this.journalDAO = journalDAO;
        this.queryObj = queryObj;
    }

    @Override
    public synchronized void run() {

//        System.out.println("开始查询 " + schemaName + " 表...");

        List<ResultSingle> resultList = null;
        boolean flag = true;
        int type = queryObj.getType();
        String condition = queryObj.getCondition();
        if (!"".equals(condition)) {
            condition = "and " + condition;
        }

        try {
            if (type == 10) {
                resultList = journalDAO.queryJinfo(schemaName, queryObj.getVendor(),
                        queryObj.getType(), condition);
            } else {
                resultList = journalDAO.queryByYear(schemaName, queryObj.getVendor(),
                        queryObj.getType(), condition);
            }
        } catch (Exception e) {
            countMap.put(new Key(0, 0), 0);
            flag = false;
            currentThread().interrupt();
        }

        if (flag) {


            for (ResultSingle resultSingle : resultList) {
                Key key;
                if (type == 10) {
                    key = new Key(resultSingle.getI_State(), -1);
                } else {
                    key = new Key(resultSingle.getI_State(), resultSingle.getI_Code());
                }
                int count = resultSingle.getCountSingle();
                if (!countMap.containsKey(key)) {
                    countMap.put(key, count);
                } else {
                    countMap.put(key, countMap.get(key) + count);
                }
            }
        }
        threadSignal.countDown();
//        System.out.println("查询 " + schemaName + " 表完成...");
    }
}

class UpdateThread extends Thread {
    private String schemaName;
    private CountDownLatch threadSignal;
    private IJournalDao journalDAO;
    private QueryObj queryObj;

    public UpdateThread(String schemaName,
                       CountDownLatch threadSignal, IJournalDao journalDAO,
                       QueryObj queryObj) {
        this.schemaName = schemaName;
        this.threadSignal = threadSignal;
        this.journalDAO = journalDAO;
        this.queryObj = queryObj;
    }

    @Override
    public synchronized void run() {

//        System.out.println("开始查询 " + schemaName + " 表...");

        String condition = queryObj.getCondition();
        if (!"".equals(condition)) {
            condition = "and " + condition;
        }

        String stateCondition = "";
        String stateFrom = queryObj.getStateFrom();
        if(!"".equals(stateFrom)){
            stateCondition = " and I_State in(" + stateFrom +") ";
        }
        String stateTo = queryObj.getStateTo();

        int beginYear = Integer.parseInt(queryObj.getBeginYear());
        int endYear = Integer.parseInt(queryObj.getEndYear());
        String yearCondition = " and (I_Code BETWEEN "+beginYear+" AND "+endYear+") ";

        int type = queryObj.getType();
        try {
            if (type == 10) {
                journalDAO.updateJinfo(schemaName, queryObj.getVendor(),
                        type, condition, stateCondition, stateTo);
            } else {
                journalDAO.updateState(schemaName, queryObj.getVendor(),
                        type, condition, stateCondition, yearCondition, stateTo);
            }

        } catch (Exception e) {
            currentThread().interrupt();
        }
        threadSignal.countDown();
    }
}

class Key implements Serializable {
    private static final long serialVersionUID = -2984419332839394589L;
    private Integer oid;
    private Integer year;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Key)) {
            return false;
        }
        if (((Key) obj).getOid().equals(oid)
                && ((Key) obj).getYear().equals(year)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return oid * 2222 + year;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Key(Integer oid, Integer year) {
        super();
        this.oid = oid;
        this.year = year;
    }
}