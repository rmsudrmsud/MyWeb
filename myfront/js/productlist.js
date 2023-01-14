// $(()=>{
//     function showList(url, page){
//         let $origin = $('div.product').first() //첫페이지에서는 아무이상 없음!
//         $('div.product').not(':first-child').remove()//product에서 origin(원본)이 아닌 나머지를 삭제, 제이쿼리에선 not함수에서는 선택자를 사용
//         $origin.show() //원본은 보여지는상태
//         $.ajax({
//             url: url,
//             method:'get',
//             data:'currentPage='+ page, /*1페이지볼라구 */
//             success:function(jsonObj){/*success는 응답이 성공되었다는 뜻, 기능(로그인, 회원가입 등등)이 실패해도 응답은 success될수 있음!*/
//             /*jsonObj는 pagebean을 json형태로 가져오는 */
//                 let list = jsonObj.list;
//                 let totalPage = jsonObj.totalPage;
//                 let currentPage = jsonObj.currentPage;
//                 let startPage = jsonObj.startPage;
//                 let endPage = jsonObj.endPage;
    
                
//                 let $origin = $('div.product').first()
//                 let $parent = $('div.productlist')
//                 $(list).each((index, p)=>{/* 번호, 배열..?*/
//                     let prodNo = p.prodNo;
//                     let prodName = p.prodName;
//                     let prodPrice = p.prodPrice;
//                     let $copy = $origin.clone()/*복제본의 영역중 div들이있음!*/
//                 //     let imgStr = '<img src="../images/' + prodNo + '.jpg">'
//                 //    $copy.find('div.img').html(imgStr)
                    
//                 let $imgObj = $('<img>') /*'<img>'라는 태그용 객체를 제이쿼리로 만드는것 */
//                     $imgObj.attr('src', '../images/' + prodNo + '.jpg')
//                     $copy.find('div.img').empty().append($imgObj)/**div 내용을 지우고  */
//                     $copy.find('div.prodNo').html(prodNo)/*copy의 하위요소찾는 find */
//                     $copy.find('div.prodName').html(prodName)/*html함수 : div태그의 열리고닫히는 내부 */
//                     $copy.find('div.prodPrice').html(prodPrice+'원')
//                     /**복제본을 만들었으니 원하는 위치에 넣어줘야함! 만들고끝 x */
//                     $parent.append($copy)
//                     /*끝났으면 원본이 화면에서 사라지게 */

//                 })
//                 $origin.hide()
    
//                 let $pageGroup = $('div.pagegroup')
//                 let pageGroupStr = ''
//                 if(startPage > 1){
//                     pageGroupStr += '<span class="'+(startPage-1)+'">[PREV]</span>'
//                 }
    
//                 if(endPage > totalPage){
//                     endPage = totalPage;
//                 }
    
//                 for(let i=startPage; i<=endPage; i++){
//                     if(i==currentPage){
//                          pageGroupStr += '<span class="current '+i+'">['+i+']</span>' 
//                          //current i 가 각각의 속성으로 current 1 이런식으로 값을 가지는데 공백을 넣어주지않으면 current1이 되어서 안됨
//                     }else{
//                         pageGroupStr += '<span class="'+i+'">['+i+']</span>'
//                     }
//                 }
//                 if(endPage < totalPage){
//                     pageGroupStr += '<span class="'+(endPage+1)+'">[NEXT]</span>'
//                     //산술연산 , 문자열연산 누가우선? 
//                     //문자열연산이 우선이라 endPage+1로쓰면 31이됨! ()로 해주기
//                 }
    
//                 $pageGroup.html(pageGroupStr)
//             },
//             error : function(xhr){
//                 alert(xhr.status)
//             }
//         })
//     }




//     let url = backURL+'/product/list'
//     //--상품 목록 요청START --
//     showList(url, 1) //주소인자값요청시 1
 
//     //--상품목록요청 END--

//     //--페이지번호 클릭되었을 때 할 일 START --
//     // $('div.pagegroup span').click(()=>{ div.pagegroup의 하위 span 클릭시 영역.
//     //  $(()={}) : 돔트리가 완성될때까지 ready이벤트가 발생되면 할 일들을 적어둠!
//     //마지막에 트리화 된 객체 함수 수행중 $.ajax 비동기요청은 요청보내놓고 다음 내용 수행하러 내려감!
//     //     span객체는 돔트리가 처음 존재할때부터 있는게아닌 임의로 만든 객체
//     //     최초의 돔트리에 존재하지 않는 객체는 클릭같은 함수로 이벤트 처리할 수 없음.
//     //     div.pagegroup은 존재하지만 span이 돔트리가 처음애 존재하지 않기때문에 수행할 수 없당
//     //     클릭대신 on 제이쿼리
//     //     alert('클릭됨')
//     // })
//     //결론 : 처음 존재하는 객체는 click 나중에 생성될 녀석들은 on 사용
//     $('div.pagegroup').on('click', 'span:not(.current)', (e)=>{ //매개변수 이름은상관 X 
//         //모든 span태그가아닌 클래스속성값이 current인 span을제외하도록 span:not(.current)
//         //클래스속성이 current인 sapn은 현재선택되어있는 페이지니까 다시클릭 할 수 없도록. 같은페이지 ajax 여러번보내면 과부화 걸림!
//         let page = $(e.target).attr('class')
//             showList(url, page)        
//     }) 
//     //--페이지번호 클릭되었을 때 할 일 END --
    
//     //--상품클릭 되었을 때 할 일 START--
//     //돔 맨처음엔 원본 상품밖에없으니 on으로
//     $('div.productlist').on('click', 'div.product',(e)=>{
//         //이벤트발생된 객체
//         //제이쿼리 parents 메서드 : 부모의 조상쪽 객체를 찾는 메서드(인자로 주는 객체까지), 바로위 부모객체 찾기 : parent
//         
//        let prodNo =  $(e.target).parents('div.product').find('div.prodNo').html()
//        location.href='./productinfo.html?prodNo='+prodNo
//     })
//     //--상품클릭 되었을 때 할 일 END--
//  })

$(()=>{ //화면랜더링 직전에 실행
    function showList(url, page){
        let $origin = $('div.product').first()
        $('div.product').not(':first-child').remove() //직전페이지의 내용이 쌓이는걸 막기위함
        $origin.show()//원본이 보여져야, 복제본이 보여짐
        $.ajax({
            url: url,
            method: 'get',
            data: 'currentPage=' + page,
            success: function(jsonObj){ //이클립스에서 받아온데이터를 function()에 
                let list = jsonObj.list;
                let totalPage = jsonObj.totalPage;  
                let currentPage = jsonObj.currentPage;
                let startPage = jsonObj.startPage;
                let endPage = jsonObj.endPage;
                          
                let $origin = $('div.product').first()
                let $parent = $('div.productlist')
                $(list).each((index, p)=>{
                    let prodNo = p.prodNo;
                    let prodName = p.prodName;
                    let prodPrice = p.prodPrice;

                    let $copy = $origin.clone()
                    // let imgStr = '<img src="../images/' + prodNo + '.jpg">'
                    // $copy.find('div.img').html(imgStr)
                    let $imgObj = $('<img>')//이미지태그를 만들어줘야함 지금은 div class이름이 img인 상태여서 !
                    $imgObj.attr('src', '../images/' + prodNo+ '.jpg')//속성추가
                    $copy.find('div.img').empty().append($imgObj)//공간을 비우지않으면 위에덮어써지지 않기 때문에 비워주고 추가
    
                    $copy.find('div.prodNo').html(prodNo)
                    $copy.find('div.prodName').html(prodName)
                    $copy.find('div.prodPrice').html(prodPrice + '원')
                    $parent.append($copy)
                })
                $origin.hide()//원본껍데기 숨기기
    
                let $pageGroup = $('div.pagegroup')
                let pageGroupStr = ''//pagegroup안의 공간을 설정하고 비워줌
                if(startPage > 1){
                    pageGroupStr += '<span class="'+(startPage-1)+'">[PREV]</span>'
                }
                //끝페이지가 총페이지를 넘지못하게
                if(endPage > totalPage){
                    endPage = totalPage;
                }
                //페이지 넘버링 
                for(let i=startPage; i<=endPage; i++){
                    if(i == currentPage){
                        //현재페이지 구분 css먹이려고 current + i
                        pageGroupStr += '<span class="current '+i+'">[' + i +']</span>'
                    }else{
                        pageGroupStr += '<span class="'+i+'">[' + i +']</span>'
                    }
                }
                //endpage의 다음페이지 가있을 때 
                if(endPage < totalPage){
                    pageGroupStr += '<span class="'+(endPage+1)+'">[NEXT]</span>'
                }
                $pageGroup.html(pageGroupStr)
            },
            error: function(xhr){
                alert(xhr.status)
            }
        })
    }





    let url = backURL + '/product/list'

    //--상품목록 요청START--
    //1페이지부터 보여줘야하니까 
    showList(url, 1)
    //--상품목록요청END--

    //--페이지번호가 클릭되었을 때 할 일 START--
    //$('div.pagegroup span').click(()=>{
    //    alert('클릭됨')
    //})

    //현재페이지가 아닌 다른페이지번호 받아오기 누를때 그 span에 current 속성부여
    $('div.pagegroup').on('click', 'span:not(.current)', (e)=>{
        let page = $(e.target).attr('class')
        showList(url, page)
    })
    //--페이지번호가 클릭되었을 때 할 일 END--


    //--상품 클릭되었을 때 할일 START--
    $('div.productlist').on('click', 'div.product', (e)=>{
        //parents 현재객체의 조상까지 가서 아래로내려오면서 product등이 여러개있을때 구분하기위해서 최상단부터가서 내려옴
        let prodNo = $(e.target).parents('div.product').find('div.prodNo').html()
        location.href='./productinfo.html?prodNo=' + prodNo
    })
    //--상품 클릭되었을 때 할일 END--
    
})
