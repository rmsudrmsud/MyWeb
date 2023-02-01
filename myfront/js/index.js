$(()=>{
    //인덱스화면 보여지자마자 호출
    checkIntervalLogined()

    // menu=productlist
    let queryStr = location.search.substring(1) //쿼리스트링 찾기
    let arr = queryStr.split('=')
    //쿼리스트링이 menu라면 ?
    if(arr[0] == 'menu'){
         switch(arr[1]){
         case 'productlist':
             $('header>nav>ul>li.productlist').click()
             break;
         }
    }
 })