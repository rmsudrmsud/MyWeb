$(()=>{
    //--상품정보보여주기 START--
    let url = backURL + '/product/info'
    let data = location.search.substring(1) //prodNo=C0001
    $.ajax({
        url: url,
        method: 'get',
        data:data,
        success: function(jsonObj){
            console.log(jsonObj)
           $('div.img>img').attr('src', '../images/' + jsonObj.prodNo+'.jpg') 
           $('div.prodname>h4').html(jsonObj.prodName)
           $('div.proddetail').html(jsonObj.prodDetail)
           $('div.prodno').html('상품번호:' + jsonObj.prodNo) 
           $('div.prodprice').html('가격:' + jsonObj.prodPrice+'원')
           $('div.prodmfdt').html('제조일자:' + jsonObj.prodMfDt)
        },
        error: function(xhr){
            alert(xhr.status)
        }
    })
    //--상품정보보여주기 END--
    
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
                //$('section').load('./productlist.html')
                location.href=frontURL+'?menu=productlist'
                break;
        }
    })
})