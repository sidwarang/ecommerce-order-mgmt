var app = angular.module('CatalogApplication', ['ui.router']);
app.config(function($stateProvider, $urlRouterProvider){
  
  $urlRouterProvider.otherwise("/");
  
    $stateProvider
    .state('root',{
        url: '/store',
        abstract: true,
        views: {
          'nav': {
              templateUrl: 'views/menu.html'
          }
        }
    })
    .state('root.products', {
        url: '/:catid',
        views: {
          'container@': {
              templateUrl: 'views/main.html',
              controller: 'productsCtrl'
          }
        }
    })
    .state('root.product', {
        url: '/:catid/:pid',
        views: {
          'container@': {
              templateUrl : 'views/main-single.html',
              controller : 'oneProdCtrl'
          }
        }
    });
});
app.run(function ($rootScope, $state, $stateParams, $http) {
  $http.get('/store/get/all/cats').success(function(data){
    $rootScope.categories = data;
    $state.transitionTo('root.products', {catid: ''});
  });
});
app.controller('productsCtrl', function($scope, $http, $stateParams){
  $scope.catID = $stateParams.catid;
  console.log($scope.catID);
  $http.get('/store/'+$stateParams.catid).success(function(data){
    $scope.products = data;
    console.log(data);
  });
});

/*
app.controller('oneProdCtrl', function($scope, $http, $stateParams){
  var pid = $stateParams.pid;
  var catid = $stateParams.catid;
  $http.get('/store/'+catid+'/'+pid).success(function(data){
    $scope.item = data;
    console.log(data);
  });
});
*/
app.controller('oneProdCtrl', function($scope, $http, $stateParams){
    var pid = $stateParams.pid;
    var catid = $stateParams.catid;
    $http.get('/store/'+catid+'/'+pid).success(function(data){
        $scope.item = data;
          console.log(data);
        var today = new Date();

    startDate = new Date($scope.item[0].sdate);
    endDate=$scope.item[0].edate
    if(endDate)
    {
      endDate=new Date(endDate);
     }

    if(!endDate)
    {
             $scope.item[0]["valid"] = 'true';


   }
  else
  {
               if(startDate < today   &&  today < endDate)
               {
                  $scope.item[0]["valid"] = 'true';

               }
               else
               {
                  $scope.item[0]["valid"] = 'false';

               }
  }
    })
});