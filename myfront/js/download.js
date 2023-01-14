$(()=>{
    //querystring 얻기
    //ex) http://192.168.0.34:5500/html/download.html?no=C0001
    // location.search : ?no=c0001// [1] : no=C0001
    let queryStr = location.search.substring(1)
    let url = backURL + 'attach/download'
    $.ajax({
        xhrFields:{
            responseType:'blob' //이미지 다운로드용 설정 httprequest의 필드 설정
        },
        cache : false, //캐시를 사용하지 않겠다.
        url:url,
        method:'get',
        data : queryStr,
        success:function(result){
            let blobStr = URL.createObjectURL(result)
            $('div.download>img').attr('src', blobStr)
        },
        error:function(){

        }
    })
    //$.ajax로 상세정보얻기(별점, 후기, 첨부파일 등등)
    $('div.detail>div.attach').html('C0001.jpg'); // 첨부파일명 보여주기

    //--첨부파일 클릭되었을 때 할일 START --
    $('div.detail>div.attach').click((e)=>{
        location.href = backURL + 'attach/download?opt=attachment&no=' + $(e.target).html()
    })
    //--첨부파일 클릭되었을 때 할일 END--


})