let backURL='http://192.168.0.18:8888/myback/'
let frontURL='http://192.168.0.18:5500/html/'


    //--로그인상태의 메뉴들 보여주기 함수 START--
    function showMenuAtLogined() {
        $('header>nav>ul>li.login').hide()
        $('header>nav>ul>li.signup').hide()
        $('header>nav>ul>li.logout').show();
        $('header>nav>ul>li.productlist').show();
    }
    //--로그인상태의 메뉴들 보여주기 함수 END--

    //--로그아웃상태의 메뉴들 보여주기 함수 START--
    function showMenuAtLogouted() {
        $('header>nav>ul>li.login').show()
        $('header>nav>ul>li.signup').show()
        $('header>nav>ul>li.logout').hide();
        $('header>nav>ul>li.productlist').show();
    }

    //--현재 로그인상태인지 로그아웃상태인가를 요청하는 함수 START--
    function checkLogined() {
        $.ajax({
            xhrFields: {
                //크로스오리진 에러를 통과할수 있는 자격! 쿠키를 host가다른 url로 요청되어도 유지할 수 있게!
                withCredentials: true
             },
            url: backURL + 'customer/checklogined',
            success: function (responseObj) {
                showMenuAtLogined();
                // //로그인된 상태일때
                // if (responseObj.status == 1) {
                //     showMenuAtLogined();
                // } else {
                // //로그아웃이거나 로그인하지 않은상태
                //     showMenuAtLogouted();
                // }
            },error : function(xhr){
                //alert(xhr.responseText)
                //확인이 끝났으니 alert 주석
                showMenuAtLogouted();
            }
        });
    }
    //--현재 로그인상태인지 로그아웃상태인가를 요청하는 함수 END--
    
    //--5초간격으로 로그인여부확인하기 함수 START--    
    function checkIntervalLogined() {
        //로드되자마자 호출하고나서 이후로 5초간격으로검사!
        checkLogined();
        //5초간격으로 checkLogined함수 호출
        window.setInterval(checkLogined, 10*1000);
    }
    //--5초간격으로 로그인여부확인하기 함수 END--



$(()=>{
    //--메뉴가 클릭되었을 때 할 일 START--
    //이부분은 돔트리가 완성되어야 할 수 있ㅇ므!
    $('header>nav>ul>li').click((e) => {
        $('header>nav>ul>li').css('background-color', '#fff').css('color', '#000')

        $(e.target).css('background-color', '#2C2A29').css('color', '#fff')
        let menu = $(e.target).attr('class')
        switch (menu) {
            case 'login':
                $('section').load('./login.html')
                break;
            case 'signup':
                $('section').load('./signup.html')
                break;
            case 'logout':
                $.ajax({
                    xhrFields: {
                        //크로스오리진 에러를 통과할수 있는 자격! 쿠키를 host가다른 url로 요청되어도 유지할 수 있게!
                        withCredentials: true
                     },
                    //로그아웃은 응답받을게없으니까 success 필요없음
                    url : backURL + 'customer/logout',
                    //응답받을내용없으니 매개변수 안씀!
                    success : function(){
                        alert('로그아웃되었습니다')
                        //location.href = frontURL
                        //로그아웃이 느렸던이유 : 백엔드 로그아웃컨트롤러에서 return"";
                        //빈문자열인데 application/json으로 보내서 에러로 빠졌던것.
                        //해결!(text/html)
                        showMenuAtLogouted()
                    }
                })
                break;
            case 'productlist':
                //$.ajax
                $('section').load('./productlist.html')
                break;
            case 'cartlist':
                $('section').load('./cartlist.html')
                break;
                //load : 제이쿼리함수. ajax get방식과 같은 효과
            case 'orderlist' :
                $('section').load('./orderlist.html')
                break;
        }
    })
    //--메뉴가 클릭되었을 때 할 일 END--

    //--로고가 클릭되었을 때 할 일 START--
    $('header>div.img').click(() => {
        location.href = frontURL
    })
    //--로고가 클릭되었을 때 할 일 END--
})
