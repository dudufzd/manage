package com.generate.job;

import com.generate.util.RedisConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Set;

@Component
public class CleanImagesJob {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Scheduled(cron = "0/2 * * * * ?")
    public void cleanImagesJob() {

        Set<String> difference = redisTemplate.opsForSet().difference(RedisConstant.SETMEAL_IMG_UPLOAD, RedisConstant.SETMEAL_IMG_DB);
        System.out.println(difference);
        if(difference!=null && difference.size()>0){//有垃圾图片
            for (String img : difference) {
                File file=new File("D:/upload/"+img);
                file.delete();
            }
        }
        redisTemplate.delete(RedisConstant.SETMEAL_IMG_UPLOAD);
        redisTemplate.delete(RedisConstant.SETMEAL_IMG_DB);
    }

}
