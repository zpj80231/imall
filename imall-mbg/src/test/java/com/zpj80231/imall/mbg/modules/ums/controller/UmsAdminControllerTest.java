package com.zpj80231.imall.mbg.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zpj80231.imall.mbg.modules.ums.entity.UmsAdmin;
import com.zpj80231.imall.mbg.modules.ums.service.UmsAdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(UmsAdminController.class)
public class UmsAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UmsAdminService mockUmsAdminService;

    @Test
    public void testFindAll() throws Exception {
        // Setup
        // Configure UmsAdminService.list(...).
        final UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId(0L);
        umsAdmin.setUsername("username");
        umsAdmin.setPassword("password");
        umsAdmin.setIcon("icon");
        umsAdmin.setEmail("email");
        umsAdmin.setNickName("nickName");
        umsAdmin.setNote("note");
        umsAdmin.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        umsAdmin.setLoginTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        umsAdmin.setStatus(0);
        final List<UmsAdmin> umsAdmins = Arrays.asList(umsAdmin);
        when(mockUmsAdminService.list()).thenReturn(umsAdmins);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/ums/umsAdmin/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getContentAsString());
    }

    @Test
    public void testFindAll_UmsAdminServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockUmsAdminService.list()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/ums/umsAdmin/list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void testFindPage() throws Exception {
        // Setup
        when(mockUmsAdminService.page(any(Page.class))).thenReturn(new Page<>(0L, 0L, 0L, false));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/ums/umsAdmin/findPage")
                        .param("pageNum", "1")
                        .param("pageSize", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(mockUmsAdminService).page(any(Page.class));
    }
}
