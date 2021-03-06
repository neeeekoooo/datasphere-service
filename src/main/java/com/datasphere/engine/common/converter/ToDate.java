/*
 * Copyright 2019, Huahuidata, Inc.
 * DataSphere is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 */

package com.datasphere.engine.common.converter;

import java.util.*;
import java.text.*;

public class ToDate
{
    public static SimpleDateFormat SDF_1;
    public static SimpleDateFormat SDF_2;
    public static SimpleDateFormat SDF_3;
    public static SimpleDateFormat SDF_4;
    public static SimpleDateFormat SDF_5;
    public static SimpleDateFormat SDF_6;
    public static SimpleDateFormat[] sdfs;
    
    static {
        ToDate.SDF_1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ToDate.SDF_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ToDate.SDF_3 = new SimpleDateFormat("yyyy-MM-dd HH");
        ToDate.SDF_4 = new SimpleDateFormat("yyyy-MM-dd");
        ToDate.SDF_5 = new SimpleDateFormat("yyyy-MM");
        ToDate.SDF_6 = new SimpleDateFormat("yyyy");
        ToDate.sdfs = new SimpleDateFormat[] { ToDate.SDF_1, ToDate.SDF_2, ToDate.SDF_3, ToDate.SDF_4, ToDate.SDF_5, ToDate.SDF_6 };
    }
    
    public Date convert(final String s) {
        throw new Error("Unresolved compilation problem: \n\tThe method convert(String) of type ToDate must override a superclass method\n");
    }
}
