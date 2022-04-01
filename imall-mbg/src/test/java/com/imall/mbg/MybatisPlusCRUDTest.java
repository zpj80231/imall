package com.imall.mbg;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imall.mbg.modules.ums.entity.UmsAdmin;
import com.imall.mbg.modules.ums.mapper.UmsAdminMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @ClassName MybatisPlusCRUDTest
 * @Version 1.0
 * @Date 2020/9/30 9:57
 * @Created by zpj80231
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusCRUDTest {

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    /**
     * 查询全部数据
     */
    @Test
    public void testSelect(){
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectList(null);
        Assert.notEmpty(umsAdmins, "umsAdmins 为空");
        umsAdmins.forEach(System.err::println);
    }

    /**
     * 查询单条数据
     */
    @Test
    public void testSelectOne(){
        UmsAdmin umsAdmin = umsAdminMapper.selectById(1);
        System.err.println(umsAdmin);
    }

    /**
     * 分页查询
     */
    @Test
    public void testSelectPage(){
        //查询条件
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.gt("id","1");
        wrapper.like("username", "o");
        //分页: baseMapper自带分页
        Page<UmsAdmin> umsAdminPage = new Page<>(1,2);
        //查询
        IPage<UmsAdmin> selectPage = umsAdminMapper.selectPage(umsAdminPage, wrapper);
        System.err.println("当前页：" + selectPage.getCurrent());
        System.err.println("每页记录数：" + selectPage.getSize());
        System.err.println("总条数：" + selectPage.getTotal());
        System.err.println("总页数：" + selectPage.getPages());
        System.err.println("数据：" + selectPage.getRecords());
        selectPage.getRecords().forEach(System.out::println);
    }

    /**
     * 新增
     */
    @Test
    public void testInsert(){
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername("zpj");
        umsAdmin.setPassword("123");
        umsAdmin.setEmail("123@12.dd.com");
        int insert = umsAdminMapper.insert(umsAdmin);
        System.err.println(insert);
        System.out.println(umsAdmin);
    }

    /**
     * 更新
     */
    @Test
    public void testUpdate(){
        /*UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(11L);
        umsAdmin.setUsername("zpj");
        umsAdmin.setEmail("222@we.sd.com");
        int updateCount = umsAdminMapper.updateById(umsAdmin);
        System.err.println(umsAdminMapper.selectById(11));*/

        /**
         * 根据 whereEntity 条件，更新记录
         *
         * @param entity        实体对象 (set 条件值,可以为 null)
         * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
         */
        int update = umsAdminMapper.update(null, Wrappers.<UmsAdmin>lambdaUpdate().set(UmsAdmin::getEmail,"123123@qwe.sd").eq(UmsAdmin::getId, 11));
        System.err.println(umsAdminMapper.selectById(11));
    }

    /**
     * 删除
     */
    @Test
    public void testDeleted(){
//        int count = umsAdminMapper.deleteById(11);
        testInsert();
        System.err.println("新增成功：" + umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().lambda().eq(UmsAdmin::getUsername,"zpj")));
        int count = umsAdminMapper.delete(Wrappers.<UmsAdmin>lambdaQuery().eq(UmsAdmin::getUsername, "zpj"));
        System.err.println("删除 " + count + " 条数据 ");
        umsAdminMapper.selectList(null).forEach(System.err::println);
    }

}
