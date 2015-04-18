var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope,$http) {
    $scope.firstName= "Jomndfbdhn";
    $scope.lastName= "Doe";

    $scope.getAlbums = function(){
        $http({
            method :'GET',
            url : '/getalbums'
        }).success(function(response){
            $scope.albums = response;
            return /*JSON.stringify(*/response/*,null,'\t')*/;
        })
    }

});
