$(()=>{
    //--상품정보보여주기 START--
    let url = backURL + 'product/info'
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
    //--장바구니 넣기 버튼 클릭 START --
    $('div.cart>button').click(()=>{
        // http://192.168.0.21:5500/html/productinfo.html?prodNo=C0001 의 쿼리스트링이 상품번호니까
        let prodNo = location.search.substring(1).split("=")[1]
        let quantity = $('div.quantity>input[type=number]').val()
        let data = {
            prodNo : prodNo,
            quantity : quantity
        }
        $.ajax({
            //장바구니테이블없이 세션에다 보관할것이니까
            xhrFields: {
                //크로스오리진 에러를 통과할수 있는 자격! 쿠키를 host가다른 url로 요청되어도 유지할 수 있게!
                withCredentials: true
             },
            url : backURL + 'cart/put',
            method : 'post',
            data : data,
            //응답받고싶으면 function(data)이후 요리조리~
            //응답받고싶은게없다면 alert를한다거나 다른상품을 보러간다거나.. 등의 작업
            success :function(){
                $('div.choose').show()
            },
            error : function(xhr){
                let jsonObj = JSON.parse(xhr.responseText);
                alert(jsonObj.msg)
            }
        })
    })
    //--장바구니 넣기 버튼 클릭 END --

    //--장바구니 보기 버튼 클릭 START--
    //--장바구니 보기 버튼 클릭 END--

    //--상품 버튼 클릭 START--
    //--상품 버튼 클릭 END--
})