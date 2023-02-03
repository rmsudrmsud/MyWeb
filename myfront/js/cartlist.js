$(()=>{
    //-- 장바구니 목록 보여주기 START--
    let $origin = $('div.cartlist>div.item').first()
    let $cartlist = $('div.cartlist')
    $.ajax({
        xhrFields: {
            //크로스오리진 에러를 통과할수 있는 자격! 쿠키를 host가다른 url로 요청되어도 유지할 수 있게!
            withCredentials: true
         },
        url:backURL + 'cart/list',
        success:function(jsonObj){
            console.log(jsonObj)
           $(jsonObj).each((index, item)=>{
                //console.log(item)
                let $copy =  $origin.clone()
                $copy.find('ul>li.prodNo').html(item.prodNo)
                $copy.find('ul>li.quantity').html(item.quantity)
                $cartlist.append($copy)

           })
           $origin.hide()
        },error(xhr){
            if(xhr.status == 400){
            alert('장바구니가 비었습니다')
            }
        }
        
    })
    //-- 장바구니 목록 보여주기 END--

    //-- 주문하기 버튼 클릭 START--
    $('div.order>button').click(()=>{

        $.ajax({
            xhrFields: {
                //크로스오리진 에러를 통과할수 있는 자격! 쿠키를 host가다른 url로 요청되어도 유지할 수 있게!
                withCredentials: true
             },
            //현재는 세션에있기때문에 방식, 전달할데이터가 없음
            url : backURL + 'order/add',
            success:function(jsonObj){
                // if(jsonObj.status == 1){
                     alert('주문하기 성공')
                     $('header>nav>ul>li.orderlist').click()
                // }else{
                //     alert('주문실패 :' +jsonObj.msg)
                // }
            },error:function(xhr){
                alert(xhr.status + ":"+'주문실패:'+xhr.responseText)
            }
        })
    }
    )
     //-- 주문하기 버튼 클릭 END--
})