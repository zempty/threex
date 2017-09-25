/*
 * 常用的工具类，整理起来，免得到处乱写
 */
;(function(rt) {
    'use strict';

    var MISC_VERSION = '0.0.1';

    var dateExt = {
        /**
         * 将日期格式化成指定格式的字符串
         * @param date 要格式化的日期，不传时默认当前时间，也可以是一个时间戳
         * @param fmt 目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
         * @returns 返回格式化后的日期字符串
         */
        formatDate: function(date, fmt) {
            date = date == undefined ? new Date() : date;
            date = typeof date == 'number' ? new Date(date) : date;
            fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
            var obj = {
                'y': date.getFullYear(), // 年份，注意必须用getFullYear
                'M': date.getMonth() + 1, // 月份，注意是从0-11
                'd': date.getDate(), // 日期
                'q': Math.floor((date.getMonth() + 3) / 3), // 季度
                'w': date.getDay(), // 星期，注意是0-6
                'H': date.getHours(), // 24小时制
                'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
                'm': date.getMinutes(), // 分钟
                's': date.getSeconds(), // 秒
                'S': date.getMilliseconds() // 毫秒
            };
            var week = ['天', '一', '二', '三', '四', '五', '六'];
            for(var i in obj) {
                fmt = fmt.replace(new RegExp(i+'+', 'g'), function(m)
                {
                    var val = obj[i] + '';
                    if(i == 'w') return (m.length > 2 ? '星期' : '周') + week[val];
                    for(var j = 0, len = val.length; j < m.length - len; j++) val = '0' + val;
                    return m.length == 1 ? val : val.substring(val.length - m.length);
                });
            }
            return fmt;
        },
        /**
         * 将字符串解析成日期
         * @param str 输入的日期字符串，如'2014-09-13'
         * @param fmt 字符串格式，默认'yyyy-MM-dd'，支持如下：y、M、d、H、m、s、S，不支持w和q
         * @returns 解析后的Date类型日期
         */
        parseDate: function(str, fmt) {
            fmt = fmt || 'yyyy-MM-dd';
            var obj = {y: 0, M: 1, d: 0, H: 0, h: 0, m: 0, s: 0, S: 0};
            fmt.replace(/([^yMdHmsS]*?)(([yMdHmsS])\3*)([^yMdHmsS]*?)/g, function(m, $1, $2, $3, $4, idx, old)
            {
                str = str.replace(new RegExp($1+'(\\d{'+$2.length+'})'+$4), function(_m, _$1)
                {
                    obj[$3] = parseInt(_$1);
                    return '';
                });
                return '';
            });
            obj.M--; // 月份是从0开始的，所以要减去1
            var date = new Date(obj.y, obj.M, obj.d, obj.H, obj.m, obj.s);
            if(obj.S !== 0) date.setMilliseconds(obj.S); // 如果设置了毫秒
            return date;
        },
        /**
         * 将一个日期格式化成友好格式，比如，1分钟以内的返回“刚刚”，
         * 当天的返回时分，当年的返回月日，否则，返回年月日
         * @param {Object} date
         */
        formatDateToFriendly: function(date) {
            date = date || new Date();
            date = typeof date === 'number' ? new Date(date) : date;
            var now = new Date();
            if((now.getTime() - date.getTime()) < 60*1000) return '刚刚'; // 1分钟以内视作“刚刚”
            var temp = this.formatDate(date, 'yyyy年M月d');
            if(temp == this.formatDate(now, 'yyyy年M月d')) return this.formatDate(date, 'HH:mm');
            if(date.getFullYear() == now.getFullYear()) return this.formatDate(date, 'M月d日');
            return temp;
        },
        /**
         * 将一段时长转换成友好格式，如：
         * 147->“2分27秒”
         * 1581->“26分21秒”
         * 15818->“4小时24分”
         * @param {Object} second
         */
        formatDurationToFriendly: function(second) {
            if(second < 60) return second + '秒';
            else if(second < 60*60) return (second-second%60)/60+'分'+second%60+'秒';
            else if(second < 60*60*24) return (second-second%3600)/60/60+'小时'+Math.round(second%3600/60)+'分';
            return (second/60/60/24).toFixed(1)+'天';
        },
        /** 
         * 将时间转换成MM:SS形式 
         */
        formatTimeToFriendly: function(second) {
            var m = Math.floor(second / 60);
            m = m < 10 ? ( '0' + m ) : m;
            var s = second % 60;
            s = s < 10 ? ( '0' + s ) : s;
            return m + ':' + s;
        },
        /**
         * 判断某一年是否是闰年
         * @param year 可以是一个date类型，也可以是一个int类型的年份，不传默认当前时间
         */
        isLeapYear: function(year) {
            if(year === undefined) year = new Date();
            if(year instanceof Date) year = year.getFullYear();
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        },
        /**
         * 获取某一年某一月的总天数，没有任何参数时获取当前月份的
         * 方式一：$.getMonthDays();
         * 方式二：$.getMonthDays(new Date());
         * 方式三：$.getMonthDays(2013, 12);
         */
        getMonthDays: function(date, month) {
            var y, m;
            if(date == undefined) date = new Date();
            if(date instanceof Date)
            {
                y = date.getFullYear();
                m = date.getMonth();
            }
            else if(typeof date == 'number')
            {
                y = date;
                m = month-1;
            }
            var days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]; // 非闰年的一年中每个月份的天数
            //如果是闰年并且是2月
            if(m == 1 && this.isLeapYear(y)) return days[m]+1;
            return days[m];
        },
        /**
         * 计算2日期之间的天数，用的是比较毫秒数的方法
         * 传进来的日期要么是Date类型，要么是yyyy-MM-dd格式的字符串日期
         * @param date1 日期一
         * @param date2 日期二
         */
        countDays: function(date1, date2) {
            var fmt = 'yyyy-MM-dd';
            // 将日期转换成字符串，转换的目的是去除“时、分、秒”
            if(date1 instanceof Date && date2 instanceof Date)
            {
                date1 = this.format(fmt, date1);
                date2 = this.format(fmt, date2);
            }
            if(typeof date1 === 'string' && typeof date2 === 'string')
            {
                date1 = this.parse(date1, fmt);
                date2 = this.parse(date2, fmt);
                return (date1.getTime() - date2.getTime()) / (1000*60*60*24);
            }
            else
            {
                console.error('参数格式无效！');
                return 0;
            }
        }
    };

    var misc = {
        isEmail : function(email) {
            var emailPattern = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))"
                    +
                    // "@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil"
                    // +
                    "@([a-zA-Z0-9-]+[.])+(cn|net|NET|com|COM|gov|GOV|mil"
                    + "|MIL|org|ORG|edu|EDU|int|INT)$"
            var re = new RegExp(emailPattern);
            if (re.test(email)) {
                return true;
            } else {
                return false;
            }
        },
        //去掉左字符串
        trimLeft : function(str) {
            var pattern = /^\s/;
            while (pattern.test(str)) {
                str = str.substring(1);
            }
            return str;
        },
        //去掉右字符串
        trimRight : function(str) {
            var pattern = /\s$/;
            while (pattern.test(str)) {
                str = str.substring(0, str.length - 1);
            }
            return str;
        },
        //判断字符串是否是NULL或空字符串，此法只用于字符串，当传入一个undefined也会返回false
        isEmpty : function(input) {
            return input == null || input == '';
        },
        //上面函数的镜面
        isNotEmpty : function(input) {
            return !this.isEmpty(input);
        },
        //是否空白字符或NULL
        isBlank : function(input) {
            return input == null || /^\s*$/.test(input);
        },
        isNotBlank : function(input) {
            return !this.isBlank(input);
        },
        trim : function(input) {
            return input.replace(/^\s+|\s+$/, '');
        },
        trimToEmpty : function(input) {
            return input == null ? "" : this.trim(input);
        },
        startsWith : function(input, prefix) {
            return input.indexOf(prefix) === 0;
        },
        endsWith : function(input, suffix) {
            return input.lastIndexOf(suffix) === 0;
        },
        contains : function(input, searchSeq) {
            return input.indexOf(searchSeq) >= 0;
        },
        equals : function(input1, input2) {
            return input1 == input2;
        },
        equalsIgnoreCase : function(input1, input2) {
            return input1.toLocaleLowerCase() == input2.toLocaleLowerCase();
        },
        containsWhitespace : function(input) {
            return this.contains(input, ' ');
        },
        // 生成指定个数的字符
        repeat : function(ch, repeatTimes) {
            var result = "";
            for (var i = 0; i < repeatTimes; i++) {
                result += ch;
            }
            return result;
        },
        //删除空白字符
        deleteWhitespace : function(input) {
            return input.replace(/\s+/g, '');
        },
        rightPad : function(input, size, padStr) {
            return input + this.repeat(padStr, size);
        },
        leftPad : function(input, size, padStr) {
            return this.repeat(padStr, size) + input;
        },
        // 首小写字母转大写
        capitalize : function(input) {
            var strLen = 0;
            if (input == null || (strLen = input.length) == 0) {
                return input;
            }
            return input.replace(/^[a-z]/, function(matchStr) {
                return matchStr.toLocaleUpperCase();
            });
        },
        // 首大写字母转小写
        uncapitalize : function(input) {
            var strLen = 0;
            if (input == null || (strLen = input.length) == 0) {
                return input;
            }
            return input.replace(/^[A-Z]/, function(matchStr) {
                return matchStr.toLocaleLowerCase();
            });
        },
        // 大写转小写，小写转大写
        swapCase : function(input) {
            return input.replace(/[a-z]/ig, function(matchStr) {
                if (matchStr >= 'A' && matchStr <= 'Z') {
                    return matchStr.toLocaleLowerCase();
                } else if (matchStr >= 'a' && matchStr <= 'z') {
                    return matchStr.toLocaleUpperCase();
                }
            });
        },
        // 统计含有的子字符串的个数
        countMatches : function(input, sub) {
            if (this.isEmpty(input) || this.isEmpty(sub)) {
                return 0;
            }
            var count = 0;
            var index = 0;
            while ((index = input.indexOf(sub, index)) != -1) {
                index += sub.length;
                count++;
            }
            return count;
        },
        // 只包含字母
        isAlpha : function(input) {
            return /^[a-z]+$/i.test(input);
        },
        // 只包含字母、空格
        isAlphaSpace : function(input) {
            return /^[a-z\s]*$/i.test(input);
        },
        // 只包含字母、数字
        isAlphanumeric : function(input) {
            return /^[a-z0-9]+$/i.test(input);
        },
        // 只包含字母、数字和空格
        isAlphanumericSpace : function(input) {
            return /^[a-z0-9\s]*$/i.test(input);
        },
        // 数字
        isNumeric : function(input) {
            return /^(?:[1-9]\d*|0)(?:\.\d+)?$/.test(input);
        },
        // 小数
        isDecimal : function(input) {
            return /^[-+]?(?:0|[1-9]\d*)\.\d+$/.test(input);
        },
        // 负小数
        isNegativeDecimal : function(input) {
            return /^\-?(?:0|[1-9]\d*)\.\d+$/.test(input);
        },
        // 正小数
        isPositiveDecimal : function(input) {
            return /^\+?(?:0|[1-9]\d*)\.\d+$/.test(input);
        },
        // 整数
        isInteger : function(input) {
            return /^[-+]?(?:0|[1-9]\d*)$/.test(input);
        },
        // 正整数
        isPositiveInteger : function(input) {
            return /^\+?(?:0|[1-9]\d*)$/.test(input);
        },
        // 负整数
        isNegativeInteger : function(input) {
            return /^\-?(?:0|[1-9]\d*)$/.test(input);
        },
        // 只包含数字和空格
        isNumericSpace : function(input) {
            return /^[\d\s]*$/.test(input);
        },
        isWhitespace : function(input) {
            return /^\s*$/.test(input);
        },
        isAllLowerCase : function(input) {
            return /^[a-z]+$/.test(input);
        },
        isAllUpperCase : function(input) {
            return /^[A-Z]+$/.test(input);
        },
        defaultString : function(input, defaultStr) {
            return input == null ? defaultStr : input;
        },
        defaultIfBlank : function(input, defaultStr) {
            return this.isBlank(input) ? defaultStr : input;
        },
        defaultIfEmpty : function(input, defaultStr) {
            return this.isEmpty(input) ? defaultStr : input;
        },
        // 字符串反转
        reverse : function(input) {
            if (this.isBlank(input)) {
                input;
            }
            return input.split("").reverse().join("");
        },
        // 删掉特殊字符(英文状态下)
        removeSpecialCharacter : function(input) {
            return input.replace(/[!-/:-@\[-`{-~]/g, "");
        },
        // 只包含特殊字符、数字和字母（不包括空格，若想包括空格，改为[ -~]）
        isSpecialCharacterAlphanumeric : function(input) {
            return /^[!-~]+$/.test(input);
        },
        /**
         * 校验时排除某些字符串，即不能包含某些字符串
         * 
         * @param {Object}
         *            conditions:里面有多个属性，如下：
         * 
         * @param {String}
         *            matcherFlag 匹配标识
         *            0:数字；1：字母；2：小写字母；3:大写字母；4：特殊字符,指英文状态下的标点符号及括号等；5:中文;
         *            6:数字和字母；7：数字和小写字母；8：数字和大写字母；9：数字、字母和特殊字符；10：数字和中文；
         *            11：小写字母和特殊字符；12：大写字母和特殊字符；13：字母和特殊字符；14：小写字母和中文；15：大写字母和中文；
         *            16：字母和中文；17：特殊字符、和中文；18：特殊字符、字母和中文；19：特殊字符、小写字母和中文；20：特殊字符、大写字母和中文；
         *            100：所有字符;
         * @param {Array}
         *            excludeStrArr 排除的字符串，数组格式
         * @param {String}
         *            length 长度，可为空。1,2表示长度1到2之间；10，表示10个以上字符；5表示长度为5
         * @param {Boolean}
         *            ignoreCase 是否忽略大小写
         *            conditions={matcherFlag:"0",excludeStrArr:[],length:"",ignoreCase:true}
         */
        isPatternMustExcludeSomeStr : function(input, conditions) {
            // 参数
            var matcherFlag = conditions.matcherFlag;
            var excludeStrArr = conditions.excludeStrArr;
            var length = conditions.length;
            var ignoreCase = conditions.ignoreCase;
            // 拼正则
            var size = excludeStrArr.length;
            var regex = (size == 0) ? "^" : "^(?!.*(?:{0}))";
            var subPattern = "";
            for (var i = 0; i < size; i++) {
                excludeStrArr[i] = Bee.StringUtils
                        .escapeMetacharacterOfStr(excludeStrArr[i]);
                subPattern += excludeStrArr[i];
                if (i != size - 1) {
                    subPattern += "|";
                }
            }
            regex = this.format(regex, [ subPattern ]);
            switch (matcherFlag) {
            case '0':
                regex += "\\d";
                break;
            case '1':
                regex += "[a-zA-Z]";
                break;
            case '2':
                regex += "[a-z]";
                break;
            case '3':
                regex += "[A-Z]";
                break;
            case '4':
                regex += "[!-/:-@\[-`{-~]";
                break;
            case '5':
                regex += "[\u4E00-\u9FA5]";
                break;
            case '6':
                regex += "[a-zA-Z0-9]";
                break;
            case '7':
                regex += "[a-z0-9]";
                break;
            case '8':
                regex += "[A-Z0-9]";
                break;
            case '9':
                regex += "[!-~]";
                break;
            case '10':
                regex += "[0-9\u4E00-\u9FA5]";
                break;
            case '11':
                regex += "[a-z!-/:-@\[-`{-~]";
                break;
            case '12':
                regex += "[A-Z!-/:-@\[-`{-~]";
                break;
            case '13':
                regex += "[a-zA-Z!-/:-@\[-`{-~]";
                break;
            case '14':
                regex += "[a-z\u4E00-\u9FA5]";
                break;
            case '15':
                regex += "[A-Z\u4E00-\u9FA5]";
                break;
            case '16':
                regex += "[a-zA-Z\u4E00-\u9FA5]";
                break;
            case '17':
                regex += "[\u4E00-\u9FA5!-/:-@\[-`{-~]";
                break;
            case '18':
                regex += "[\u4E00-\u9FA5!-~]";
                break;
            case '19':
                regex += "[a-z\u4E00-\u9FA5!-/:-@\[-`{-~]";
                break;
            case '20':
                regex += "[A-Z\u4E00-\u9FA5!-/:-@\[-`{-~]";
                break;
            case '100':
                regex += "[\s\S]";
                break;
            default:
                alert(matcherFlag + ":This type is not supported!");
            }
            regex += this.isNotBlank(length) ? "{" + length + "}" : "+";
            regex += "$";
            var pattern = new RegExp(regex, ignoreCase ? "i" : "");
            return pattern.test(input);
        },
        /**
         * @param {String}
         *            message
         * @param {Array}
         *            arr 消息格式化
         */
        format : function(message, arr) {
            return message.replace(/{(\d+)}/g, function(matchStr, group1) {
                return arr[group1];
            });
        },
        /**
         * 把连续出现多次的字母字符串进行压缩。如输入:aaabbbbcccccd 输出:3a4b5cd
         * 
         * @param {String}
         *            input
         * @param {Boolean}
         *            ignoreCase : true or false
         */
        compressRepeatedStr : function(input, ignoreCase) {
            var pattern = new RegExp("([a-z])\\1+", ignoreCase ? "ig" : "g");
            return result = input.replace(pattern, function(matchStr, group1) {
                return matchStr.length + group1;
            });
        },
        /**
         * 校验必须同时包含某些字符串
         * 
         * @param {String}
         *            input
         * @param {Object}
         *            conditions:里面有多个属性，如下：
         * 
         * @param {String}
         *            matcherFlag 匹配标识
         *            0:数字；1：字母；2：小写字母；3:大写字母；4：特殊字符,指英文状态下的标点符号及括号等；5:中文;
         *            6:数字和字母；7：数字和小写字母；8：数字和大写字母；9：数字、字母和特殊字符；10：数字和中文；
         *            11：小写字母和特殊字符；12：大写字母和特殊字符；13：字母和特殊字符；14：小写字母和中文；15：大写字母和中文；
         *            16：字母和中文；17：特殊字符、和中文；18：特殊字符、字母和中文；19：特殊字符、小写字母和中文；20：特殊字符、大写字母和中文；
         *            100：所有字符;
         * @param {Array}
         *            excludeStrArr 排除的字符串，数组格式
         * @param {String}
         *            length 长度，可为空。1,2表示长度1到2之间；10，表示10个以上字符；5表示长度为5
         * @param {Boolean}
         *            ignoreCase 是否忽略大小写
         *            conditions={matcherFlag:"0",containStrArr:[],length:"",ignoreCase:true}
         * 
         */
        isPatternMustContainSomeStr : function(input, conditions) {
            // 参数
            var matcherFlag = conditions.matcherFlag;
            var containStrArr = conditions.containStrArr;
            var length = conditions.length;
            var ignoreCase = conditions.ignoreCase;
            // 创建正则
            var size = containStrArr.length;
            var regex = "^";
            var subPattern = "";
            for (var i = 0; i < size; i++) {
                containStrArr[i] = Bee.StringUtils
                        .escapeMetacharacterOfStr(containStrArr[i]);
                subPattern += "(?=.*" + containStrArr[i] + ")";
            }
            regex += subPattern;
            switch (matcherFlag) {
            case '0':
                regex += "\\d";
                break;
            case '1':
                regex += "[a-zA-Z]";
                break;
            case '2':
                regex += "[a-z]";
                break;
            case '3':
                regex += "[A-Z]";
                break;
            case '4':
                regex += "[!-/:-@\[-`{-~]";
                break;
            case '5':
                regex += "[\u4E00-\u9FA5]";
                break;
            case '6':
                regex += "[a-zA-Z0-9]";
                break;
            case '7':
                regex += "[a-z0-9]";
                break;
            case '8':
                regex += "[A-Z0-9]";
                break;
            case '9':
                regex += "[!-~]";
                break;
            case '10':
                regex += "[0-9\u4E00-\u9FA5]";
                break;
            case '11':
                regex += "[a-z!-/:-@\[-`{-~]";
                break;
            case '12':
                regex += "[A-Z!-/:-@\[-`{-~]";
                break;
            case '13':
                regex += "[a-zA-Z!-/:-@\[-`{-~]";
                break;
            case '14':
                regex += "[a-z\u4E00-\u9FA5]";
                break;
            case '15':
                regex += "[A-Z\u4E00-\u9FA5]";
                break;
            case '16':
                regex += "[a-zA-Z\u4E00-\u9FA5]";
                break;
            case '17':
                regex += "[\u4E00-\u9FA5!-/:-@\[-`{-~]";
                break;
            case '18':
                regex += "[\u4E00-\u9FA5!-~]";
                break;
            case '19':
                regex += "[a-z\u4E00-\u9FA5!-/:-@\[-`{-~]";
                break;
            case '20':
                regex += "[A-Z\u4E00-\u9FA5!-/:-@\[-`{-~]";
                break;
            case '100':
                regex += "[\s\S]";
                break;
            default:
                alert(matcherFlag + ":This type is not supported!");
            }
            regex += this.isNotBlank(length) ? "{" + length + "}" : "+";
            regex += "$";
            var pattern = new RegExp(regex, ignoreCase ? "i" : "");
            return pattern.test(input);
        },
        // 中文校验
        isChinese : function(input) {
            return /^[\u4E00-\u9FA5]+$/.test(input);
        },
        // 去掉中文字符
        removeChinese : function(input) {
            return input.replace(/[\u4E00-\u9FA5]+/gm, "");
        },
        // 转义元字符
        escapeMetacharacter : function(input) {
            var metacharacter = "^$()*+.[]|\\-?{}|";
            if (metacharacter.indexOf(input) >= 0) {
                input = "\\" + input;
            }
            return input;
        },
        // 转义字符串中的元字符
        escapeMetacharacterOfStr : function(input) {
            return input.replace(/[\^\$\*\+\.\|\\\-\?\{\}\|]/gm, "\\$&");
        }
    };

    //绑定扩展的日期对象到misc对象身上
    misc.datee = dateExt;
    // 绑定misc到window对象里，才能在外面使用
    rt.misc = misc;
})(window || this);// window对象存在就用window对象，不存在说明不是浏览器环境，就用调用它的那个对象
