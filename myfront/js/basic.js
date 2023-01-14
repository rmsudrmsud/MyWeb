let backURL='http://localhost:8888/myback/'
let frontURL='http://localhost:5500/html/'
$(()=>{
    //--메뉴가 클릭되었을 때 할 일 START--
    $('header>nav>ul>li').click((e)=>{
        $('header>nav>ul>li').css('background-color', '#fff').css('color', '#000')
        $(e.target).css('background-color', '#2C2A29').css('color', '#fff')
        let menu = $(e.target).attr('class')
        switch(menu){
            case 'login':
                break;
            case 'signup':
                break;
            case 'logout':
                break;
            case 'productlist':
                //$.ajax
                $('section').load('./productlist.html')
                break;
        }
    })
     //--메뉴가 클릭되었을 때 할 일 END--

     //--로고가 클릭되었을 때 할 일 START--
     $('header>div.img').click(()=>{
        location.href=frontURL
     })
     //--로고가 클릭되었을 때 할 일 END--
     
})