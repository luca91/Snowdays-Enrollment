<!DOCTYPE html>
<html lang="en">
<head>
<title>Bolzano Snowdays - Enrollment</title>
<meta charset="utf-8">
<link rel="stylesheet" href="public/css/reset.css" type="text/css" media="screen">
<link rel="stylesheet" href="public/css/style.css" type="text/css" media="screen">
<link rel="stylesheet" href="public/css/layout.css" type="text/css" media="screen">
 <script src="public/js//firebug-lite.js" type="text/javascript"></script>
<script src="public/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="public/js/cufon-yui.js" type="text/javascript"></script>
<script src="public/js/cufon-replace.js" type="text/javascript"></script>
<script src="public/js/Open_Sans_400.font.js" type="text/javascript"></script>
<script src="public/js/Open_Sans_Light_300.font.js" type="text/javascript"></script> 
<script src="public/js/Open_Sans_Semibold_600.font.js" type="text/javascript"></script>  
<script type="text/javascript" src="public/js/tms-0.3.js"></script>
<script type="text/javascript" src="public/js/tms_presets.js"></script> 
<script type="text/javascript" src="public/js/jquery.easing.1.3.js"></script> 
<script src="public/js/FF-cash.js" type="text/javascript"></script>
<!--[if lt IE 7]>
        <div style=' clear: both; text-align:center; position: relative;'>
                <a href="http://www.microsoft.com/windows/internet-explorer/default.aspx?ocid=ie6_countdown_bannercode"><img src="http://www.theie6countdown.com/images/upgrade.jpg" border="0"  alt="" /></a>
        </div>
<![endif]-->
<!--[if lt IE 9]>
        <script type="text/javascript" src="js/html5.js"></script>
        <link rel="stylesheet" href="css/ie.css" type="text/css" media="screen">
<![endif]-->
</head>
<body id="page1">
<!-- header -->
        <div class="bg">
                <div class="main">
                        <header>
                                <div class="row-1">                                      
                                </div>
                                <div class="row-2">                                       
                                                <div class="menu">                                                                                        
                                                  <a class="button-2" href="/snowdays-enrollment/login.html">Login</a>                                                  
                                                </div>                                      
                                </div>
                                <div class="row-3">
                                        <div class="slider-wrapper">
                                                <div class="slider">
                                                  <ul class="items">
                                                        <li><img src="../images/slider-img1.jpg" alt="">
                                                                <strong class="banner">
                                                                        <strong class="b1">Save the date:</strong>
                                                                        <strong class="b2">Snowdays 2014</strong>
                                                                        <strong class="b3">Three days of sports, snow and fun!<br>Next March, 2014!</strong>
                                                                </strong>
                                                        </li>
                                                        <li><img src="snowdays-enrollment/public/images/slider-img2.jpg" alt="">
                                                                <strong class="banner">
                                                                        <strong class="b1">Summer is close:</strong>
                                                                        <strong class="b2">Big Concert!</strong>
                                                                        <strong class="b3">Reserve you place for the big night<br>at Museion at mid June!</strong>
                                                                </strong>
                                                        </li>
                                                        <li><img src="snowdays-enrollment/public/images/slider-img3.jpg" alt="">
                                                                <strong class="banner">
                                                                        <strong class="b1">Time has come:</strong>
                                                                        <strong class="b2">Degree Ceremony</strong>
                                                                        <strong class="b3">Enroll to the next graduation ceremony!</strong>
                                                                </strong>
                                                        </li>
                                                  </ul>
                                                  <a class="prev" href="#">prev</a>
                                                  <a class="next" href="#">prev</a>
                                                </div>
                                        </div>
                                </div>
                        </header>
                     
<!-- footer -->
                        <footer>
                                <div class="row-top">
                                        <div class="row-padding">
                                                <div class="wrapper">
                                                        <div class="col-1">
                                                                <h4>Address:</h4>
                                                                <dl class="address">
                                                                        <dt><span>Country:</span>Italy</dt>
                                                                        <dd><span>City:</span>Bolzano</dd>
                                                                        <dd><span>Address:</span>Universitaetzplatz 1</dd>
                                                                        <dd><span>Email:</span><a href="#">info@ems.unibz.it</a></dd>
                                                                </dl>
                                                        </div>
                                                        <div class="col-2">
                                                                <h4>Follow Us:</h4>
                                                                <ul class="list-services">
                                                                        <li class="item-1"><a href="https://www.facebook.com/Bolzano.Snowdays">Facebook</a></li>
                                                                        <li class="item-2"><a href="https://twitter.com/bolzanosnowdays">Twitter</a></li>
                                                                        <li class="item-3"><a href="http://vimeo.com/user5962140">Vimeo</a></li>
                                                                        
                                                                </ul>
                                                        </div>
                                                        
                                                        <div class="col-4">
                                                           <div class="indent6">
                                                                <img src="${pageContext.request.contextPath}/public/images/logo_verticale_2014.png" alt="" height="180" width="120">
                                                           </div>
                                                        </div>
                                                </div>
                                        </div>
                                </div>                                
                        </footer>
                </div>
        </div>
        <script type="text/javascript"> Cufon.now(); </script>
        <script type="text/javascript">
                $(function(){
                        $('.slider')._TMS({
                                prevBu:'.prev',
                                nextBu:'.next',
                                playBu:'.play',
                                duration:800,
                                easing:'easeOutQuad',
                                preset:'simpleFade',
                                pagination:false,
                                slideshow:3000,
                                numStatus:false,
                                pauseOnHover:true,
                                banners:true,
                                waitBannerAnimation:false,
                                bannerShow:function(banner){
                                        banner
                                                .hide()
                                                .fadeIn(500)
                                },
                                bannerHide:function(banner){
                                        banner
                                                .show()
                                                .fadeOut(500)
                                }
                        });
                })
        </script>
</body>
</html>