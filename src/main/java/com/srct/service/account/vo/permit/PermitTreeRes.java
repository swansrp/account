/**
 * Title: PermitTreeRes.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-5 14:23
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.permit
 */
package com.srct.service.account.vo.permit;

import com.srct.service.account.bo.permit.PermitBO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PermitTreeRes {
    private PermitBO menu;
    private List<PermitTreeRes> subMenu;

    public PermitTreeRes() {
        subMenu = new ArrayList<>();
    }
}
