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
    
    $scope.getPhotos = function(val){
        $scope.album_id = val;
        $http({
            method: 'GET',
            url: '/'+$scope.album_id+'/photos'
        }).success(function(response){
            $scope.photos = response;
            return;
        });
    }
    
    $scope.backupAlbum = function(val){
        $scope.album_id = val;
        $http({
            method: 'GET',
            url: '/backup?album_id='+$scope.album_id
        }).success(function(response){
            $scope.status = response;
            return;
        });
    }

});
