package com.example.commonlib.util;

public enum SizeConverter {
    /**
     * 转换任意单位的大小, 返回结果会包含两位小数但不包含单位.
     */
    Arbitrary {
        @Override
        public String convert(float size) {
            while (size > 1024) {
                size /= 1024;
            }
            return String.format(FORMAT_F, size);
        }
    },

    // -----------------------------------------------------------------------
    // 有单位

    /**
     * 转换单位为B的大小, 返回结果会包含两位小数以及单位. 如: 1024B->1KB, (1024*1024)B->1MB
     */
    B {
        @Override
        public String convert(float B) {
            return converter(0, B);
        }
    },

    /**
     * 转换单位为KB的大小, 返回结果会包含两位小数以及单位.
     */
    KB {
        @Override
        public String convert(float KB) {
            return converter(1, KB);
        }
    },

    /**
     * 转换单位为MB的大小, 返回结果会包含两位小数以及单位.
     */
    MB {
        @Override
        public String convert(float MB) {
            return converter(2, MB);
        }
    },

    /**
     * 转换单位为GB的大小, 返回结果会包含两位小数以及单位.
     */
    GB {
        @Override
        public String convert(float GB) {
            return converter(3, GB);
        }
    },

    /**
     * 转换单位为TB的大小, 返回结果会包含两位小数以及单位.
     */
    TB {
        @Override
        public String convert(float TB) {
            return converter(4, TB);
        }
    },

    // -----------------------------------------------------------------------
    // trim没单位
    /**
     * 转换任意单位的大小, 返回结果小数部分为0时将去除两位小数, 不包含单位.
     */
    ArbitraryTrim {
        @Override
        public String convert(float size) {
            while (size > 1024) {
                size /= 1024;
            }

            int sizeInt = (int) size;
            boolean isfloat = size - sizeInt > 0.0F;
            if (isfloat) {
                return String.format(FORMAT_F, size);
            }
            return String.format(FORMAT_D, sizeInt);
        }
    },

    // -----------------------------------------------------------------------
    // trim有单位
    /**
     * 转换单位为B的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    BTrim {
        @Override
        public String convert(float B) {
            return trimConverter(0, B);
        }
    },
    /**
     * 转换单位为KB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    KBTrim {
        @Override
        public String convert(float KB) {
            return trimConverter(1, KB);
        }
    },
    /**
     * 转换单位为MB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    MBTrim {
        @Override
        public String convert(float MB) {
            return trimConverter(2, MB);
        }
    },
    /**
     * 转换单位为GB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    GBTrim {
        @Override
        public String convert(float GB) {
            return trimConverter(3, GB);
        }
    },
    /**
     * 转换单位为TB的大小, 返回结果小数部分为0时将去除两位小数, 会包含单位.
     */
    TBTrim {
        @Override
        public String convert(float TB) {
            return trimConverter(4, TB);
        }
    };

    /***
     * <p> 将指定的大小转换到1024范围内的大小. 注意该方法的最大单位为PB, 最小单位为B,
     * 任何超出该范围的单位最终会显示为**. </p>
     *
     * @param size 要转换的大小, 注意是浮点数, 不要以整形的方式传入, 容易造成溢出.
     *             (如: 1024*1024*1024*1024*1024会溢出, 使结果为0, 因为它先将结果以int相乘后再转换为float;
     *             而1024.0F*1024.0F*1024.0F*1024.0F*1024.0F就不会溢出)
     * @return
     */
    abstract public String convert(float size);

    // -----------------------------------------------------------------------
    // 单位转换

    private static final String[] UNITS = new String[]{
            "B", "KB", "MB", "GB", "TB", "PB", "**"
    };

    private static final int LAST_IDX = UNITS.length - 1;

    private static final String FORMAT_F = "%1$-1.2f";
    private static final String FORMAT_F_UNIT = "%1$-1.2f%2$s";

    private static final String FORMAT_D = "%1$-1d";
    private static final String FORMAT_D_UNIT = "%1$-1d%2$s";

    // -----------------------------------------------------------------------
    private static String converter(int unit, float size) {
        int unitIdx = unit;
        while (size > 1024) {
            unitIdx++;
            size /= 1024;
        }
        int idx = unitIdx < LAST_IDX ? unitIdx : LAST_IDX;
        return String.format(FORMAT_F_UNIT, size, UNITS[idx]);
    }

    private static String trimConverter(int unit, float size) {
        int unitIdx = unit;
        while (size > 1024) {
            unitIdx++;
            size /= 1024;
        }

        int sizeInt = (int) size;
        boolean isfloat = size - sizeInt > 0.0F;
        int idx = unitIdx < LAST_IDX ? unitIdx : LAST_IDX;
        if (isfloat) {
            return String.format(FORMAT_F_UNIT, size, UNITS[idx]);
        }
        return String.format(FORMAT_D_UNIT, sizeInt, UNITS[idx]);
    }

    // -----------------------------------------------------------------------
    public static String convertBytes(float B, boolean trim) {
        return trim ? trimConvert(0, B, true) : convert(0, B, true);
    }

    public static String convertKB(float KB, boolean trim) {
        return trim ? trimConvert(1, KB, true) : convert(1, KB, true);
    }

    public static String convertMB(float MB, boolean trim) {
        return trim ? trimConvert(2, MB, true) : convert(2, MB, true);
    }

    /***
     * <p> 存储大小单位间的转换. 注意该方法的最大单位为PB, 最小单位为B,
     * 任何超出该范围的单位最终会显示为**. </p>
     *
     * @param unit     从哪个单位开始
     * @param size     存储大小, 注意是float, 不要以整形的形式传入, 否则会溢出(如:1024*1024这种,
     *                 它是先将1024*1024作为int相乘再转换为float的, 如果值过大的话就会溢出了,
     *                 所以这么写1024.0F*1024.0F)
     * @param withUnit 返回的结果字符串是否带有对应的单位
     * @return
     */
    private static String convert(int unit, float size, boolean withUnit) {
        int unitIdx = unit;
        while (size > 1024) {
            unitIdx++;
            size /= 1024;
        }
        if (withUnit) {
            int idx = unitIdx < LAST_IDX ? unitIdx : LAST_IDX;
            return String.format(FORMAT_F_UNIT, size, UNITS[idx]);
        }
        return String.format(FORMAT_F, size);
    }

    /***
     * <p> 存储大小单位间的转换, 如果转换后小数部分为0, 则去除小数部分.
     * 注意该方法的最大单位为PB, 最小单位为B, 任何超出该范围的单位最终会显示为**. </p>
     *
     * @param unit     从哪个单位开始
     * @param size     存储大小, 注意是float, 不要以整形的形式传入, 否则会溢出(如:1024*1024这种,
     *                 它是先将1024*1024作为int相乘再转换为float的, 如果值过大的话就会溢出了,
     *                 所以这么写1024.0F*1024.0F)
     * @param withUnit 返回的结果字符串是否带有对应的单位
     * @return
     */
    private static String trimConvert(int unit, float size, boolean withUnit) {
        int unitIdx = unit;
        while (size > 1024) {
            unitIdx++;
            size /= 1024;
        }

        int sizeInt = (int) size;
        boolean isfloat = size - sizeInt > 0.0F;
        if (withUnit) {
            int idx = unitIdx < LAST_IDX ? unitIdx : LAST_IDX;
            if (isfloat) {
                return String.format(FORMAT_F_UNIT, size, UNITS[idx]);
            }
            return String.format(FORMAT_D_UNIT, sizeInt, UNITS[idx]);
        }

        if (isfloat) {
            return String.format(FORMAT_F, size);
        }
        return String.format(FORMAT_D, sizeInt);
    }
}
