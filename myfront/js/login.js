//돔트리가완성될때까지 기다리기
//이벤트는 한객체에서 발생한게 다른객체에 전달되기도하고 한객체에서 발생된게 다른객체에서 발동되기도함!
//폼에서 sbumit 이벤트가 발생되면 form도 자동으로 발생됨 
$(()=>{
    //--form객체의 submit이벤트 발생시 할 일 START--
        $('div.login>form').submit(()=>{
            //input 태그의 name속성의 녀석들의 값
            let idValue = $('input[name=id]').val()
            let pwdValue = $('input[name=pwd]').val()
            let url = backURL + 'customer/login'
            let data = $('div.login>form').serialize()
            console.log("serialize값:", data)
            
            $.ajax({
                xhrFields: {
                    //크로스오리진 에러를 통과할수 있는 자격! 쿠키를 host가다른 url로 요청되어도 유지할 수 있게!
                    withCredentials: true
                 },
                url:url,
                method:'post',
               //1.방법
                //data:'id='+idValue + 'pwd='+pwdValue
               
                //2.방법 자바스크립트 객체로담는방법 : { }에 담기
                //data:{id: idValue, pwd:pwdValue},

                //3.방법 폼객체 하위태그의 입력된 값이 자동으로 만들어짐! serialize
                //입력할 값이 너무많을때 1,2번방법이 불편하니 이런방법 !
                data:data,
                success:function(jsonObj){
                    //로그인이 성공되었을 때 !
                    console.log(jsonObj)
                    location.href = frontURL
                },
                error : function(xhr){
                    //xhr : 제이쿼리 html request..?
                    
                    alert(xhr.responseText)
                }
            })

            //기본이벤트처리 막아주기 위의 ajax가 처리된 이후 폼태그의 기본submit을 하러가기때문에
            //form객체의 기본submit처리는 ? 폼태그의 기본내장된 처리가있음 form action=""속성의 url로 사용자가 입력한 값을 전송함
            //e.prevendDefault() //기본 이벤트 처리를 안함
            //e.stopPropagation() //이벤트 전파 중지 

            //폼태그에 액션속성없고 return false없으면 현재url로 다시 (로그인값)정보가감!
            //다 처리를 했는데 return false 윗단에서 오타가난다면 
            //ajax가 망가지기때문에 ajax영역을 무시하고 기본이벤트 처리하러감..! ajax많이 확인..!
            return false
        })
    //--form객체의 submit이벤트 발생시 할 일 END--
})