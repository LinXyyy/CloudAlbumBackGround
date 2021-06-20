package com.xy.service.impl;

import com.xy.dao.MomentMapper;
import com.xy.dao.PictureMapper;
import com.xy.pojo.Picture;
import com.xy.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author x1yyy
 */
@Service
public class MomentServiceImpl implements MomentService {

    @Autowired
    private MomentMapper momentMapper;
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public Map<String, Object> createMoment(String userKey) {
        Map<String, Object> results = new HashMap<>();

        Map<String, Object> result = new HashMap<>();

        List<Picture> pictures = thatYearToday(userKey);

        if (pictures.size() > 0) {
            result.put("id", (int) (Math.random() * (999999 - 100000) + 100000));
            result.put("那年今日", pictures);
        }

        Map<Integer, List<Picture>> map = sceneryAlongTheWay(userKey);

        for (Integer integer : map.keySet()) {
            result.put("id", (int) (Math.random() * (999999 - 100000) + 100000));
            result.put(integer + ",沿途的风景", map.get(integer));
        }

        results.put("resultCode", 0);
        results.put("resultMessage", "success");
        results.put("data", result);

        return results;
    }

    @Override
    public Map<String, Object> saveMoment(String id, String userKey) {

        return null;
    }

    /**
     * 那年今日剪辑
     * @param userKey 用户
     * @return 结果
     */
    private List<Picture> thatYearToday(String userKey) {
        return pictureMapper.getPictureByUserKeyAndNowTime(Integer.parseInt(userKey));
    }

    /**
     * 沿途的风景剪辑
     * @param userKey 用户
     * @return 结果
     */
    private Map<Integer, List<Picture>> sceneryAlongTheWay(String userKey) {
        List<Picture> pictures = pictureMapper.getPictureByUserKeyAndScenery(Integer.parseInt(userKey), "自然风光");

        return pictures.stream().collect(Collectors.groupingBy(picture -> picture.getDate().getYear() + 1900));
    }

    /**
     * 共度剪辑
     * @param userKey 用户
     */
    private void spendTogether(String userKey) {

    }
}
