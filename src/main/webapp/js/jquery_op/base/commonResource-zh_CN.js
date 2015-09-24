
var errorMessage = {
	_401 : "401异常，您访问了未授权功能，如有疑问请与管理员联系。",
	_407 : "407异常，请重新登录。",
	_500 : "应用异常：",
	_501 : "外部接口系统异常：",
	_404 : "未找到指定页面。",
	_409 : "修改版本发生冲突。",
	_num : "只能输入整数！",
	_maxNum : "不能超过最大页数！",
	_number : "你输入的不正确,请输入数值字符!!",
	//补全其他的异常
	_400 : "错误的请求",
	_403 : "禁止访问",
	_405 : "用来访问本页面的 HTTP谓词不被允许",
	_406 : "客户端浏览器不接受所请求页面的MIME类型",
	_412 : "前提条件失败",
	_413 : "请求实体太大",
	_414 : "请求URI太长",
	_415 : "不支持的媒体类型",
	_416 : "所请求的范围无法满足",
	_417 : "执行失败",
	_423 : "锁定的错误",
	_502 : "Web服务器用作网关或代理服务器时收到了无效响应",
	_503 : "服务不可用",
	_504 : "网关超时",
	_505 : "HTTP版本不受支持"
	
}

var checkboxMessage={
	needSelected : "请选择一项",
	singleSelected : "只能选择一项",
	notCheckOnDelect : "请选择要删除的项",
	notCheckOnDetail :"请选择要查看的项",
	tooMoreOnDetail :  "不能同时查看多项"
}
var showDialog = {
	ieOrFirefox : " 请使用ie浏览器或 firfox 3.0或以上版本得浏览器",
	closeTab    : " 确定要关闭当前页签吗？"
} 

var operationMessage={
	operationSuccess : "操作成功",
	operationFailure  :"操作失败",
	deleteConfirm : "您确定删除记录吗?"
}
var formValidateCh={
	notNull:"不能为空",
	timeFormatNotDefine:"该时间格式没有定义",
	timeFormatNotLegality:"时间不合法，或者格式错误，或者其他错误，要求格式为yyyyMMdd",
	timeFormatBiasNotLegality:"时间不合法，或者格式错误，或者其他错误，要求格式为yyyy/MM/dd",
	timeFormatLandNotLegality:"时间不合法，或者格式错误，或者其他错误，要求格式为yyyy-MM-dd",
	timeFormatScapeHmsNotLegality:"时间格式不合法，或者格式错误，或者其他错误，要求格式为yyyy-MM-dd HH:mm:ss",
	timeFormatHmsNotLegality:"时间格式不合法，或者格式错误，或者其他错误，要求格式为yyyyMMddHHmmss",
	timeWrong:"时间出错",
	forbiddenCharacter:"为非法字符，包含了#，$，%，^，&，*，<，>,\,',\",+",
	forbiddenCharacterWOPlus:"为非法字符，包含了#，$，%，^，&，*，<，>,\,',\"",
	parenError:"括号不匹配",
	parenPosError:"括号位置错误",
	countryCdError:"国家代码必须在号码最前",
	countryCdNotNull:"国家代码必须在括号内",
	countryCd:"国家代码",
	phonenumberLengthError:"手机号码最多为17位",
	phoneLengthError:"电话号码最多为17位",
	checkLengInputLess:"请至少输入",
	checkLengChar:"个字符",
	checkLengInputMore:"请最多输入 ",
	phoneWrong:"电话号码错误，电话号码格式为区号+电话号码+分机号码，电话号码和分机号之间用-分隔,最长为17位,最短为10位",
	phoneWrong1:"电话号码错误，电话号码格式为区号+电话号码+分机号码,最长为17位,最短为10位",
	phoneAddressChange:"电话号码错误,不能超过16位",
	phoneFormat:"输入电话格式有错，请参考(区号-电话号-分机号)",
	plusPositionError:"+只能放在电话号码的首位",
	countryCodeError:"国家代码只能为86",
	emailNotLegality:"邮箱地址不合法",
	isChinaIDCardLast:"身份证号码校验位错:最后一位应该为:",
	isChinaIDCardLegality:"身份证不合法",
	isChinaIDCardWrong:"身份证号码错误",
	isChinaIDCard:"输入的身份证号码必须为15位或者18位！",
	isChinaIDCardLast:"身份证效验位错误!正确为：",
	isChinaIDCardYear:"身份证年份错误",
	isChinaIDCardYearBetween:"年份应该在1900-2050之间!",
	isChinaIDCardLength:"身份证长度出错",
	isChinaIDCardDate:"身份证中日期信息不正确",
	isChinaIDCardMonth:"月份输入有误,请重新输入!",
	isChinaIDCardMonthBetween:"月份应该在1-12之间!",
	numWrong:"数值含有错误字符",
	numWrongLength:"数值超过指定长度",
	intWrong:"超过整型范围",
	longWrong:"超过long范围",
	isChinaIDCardTimeWrong:"身份证号码内日期错误",
	mobileWrong:"手机号码错误",
	numAndCharWrong:"只能含有数字和字母",
	zipLongWrong:"邮编长度错误",
	chLongWrong:"文字长度出错，文字长度不能超过",
	doubleDotWrong:"小数格式错误，小数的第一位和最后一位不能有.",
	doublePrecisionWrong:"小数精度错误，小数点后不能超过2位",
	doubleStyleWrong:"小数格式错误",
	numberWrong:"整数部分最长为",
	timeFormatError:"格式错误：请输入“HH:MM”格式。值范围(00:00--23:59)",
	phoneAndMobileWrong:"手机号或电话号出错",
	callIdIsNull:"没有对应的录音文件",
	gt0interror:"请输入大于0的整数",
	endtimeLessBegintime:"结束时间不能小于开始时间",
	carHeadWrong:"车牌号码只以汉字或WJ开头，不支持临时车牌(LS)开头",
	carBodyWrong:"车牌号码汉字只出现在前两位",
	carNoLessWrong:"车牌号码长度不能少于6位，\"WJ\"作为一位！",
	carmaxWrong:"车牌号码长度不能大于",
	textMaxWrong:"汉字占两位，字母数字占一位",
	policyLengthWrong:"输入错误，保单号应为15位。如为组合产品，请输入14位并依次在末位加1、2、3进行查询"
}



