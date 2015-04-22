var app = angular.module('myApp', []);
app.controller('myCtrl', function ($scope, $http) {

	$scope.dispVal = 'false';
	$scope.detailDispVal = 'false';

<<<<<<< HEAD
    $scope.showModal = false;
    $scope.toggleModal = function(val){
        $scope.album_id = val;
        $scope.showModal = !$scope.showModal;
        $http({
            method :'GET',
            url : '/getalbummeta?album_id='+ $scope.album_id
        }).success(function(response){
            $scope.albums = response;
            return /*JSON.stringify(*/response/*,null,'\t')*/;
        })
    };
});


app.directive('modal', function () {
    return {
        template: '<div class="modal fade">' +
        '<div class="modal-dialog">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
        '<h4 class="modal-title">{{ title }}</h4>' +
        '</div>' +
        '<div class="modal-body" ng-transclude></div>' +
        '</div>' +
        '</div>' +
        '</div>',
        restrict: 'E',
        transclude: true,
        replace:true,
        scope:true,
        link: function postLink(scope, element, attrs) {
            scope.title = attrs.title;

            scope.$watch(attrs.visible, function(value){
                if(value == true)
                    $(element).modal('show');
                else
                    $(element).modal('hide');
            });

            $(element).on('shown.bs.modal', function(){
                scope.$apply(function(){
                    scope.$parent[attrs.visible] = true;
                });
            });

            $(element).on('hidden.bs.modal', function(){
                scope.$apply(function(){
                    scope.$parent[attrs.visible] = false;
                });
            });
        }
    };
});
=======
	$scope.getAlbums = function () {
		$scope.dispVal = 'true';
		$http({
			method: 'GET',
			url: '/getalbums'
		}).success(function (response) {
			$scope.albums = response;
			return response;
		})
	}

	$scope.getPhotos = function (val) {
		$scope.album_id = val;
		$http({
			method: 'GET',
			url: '/' + $scope.album_id + '/photos'
		}).success(function (response) {
			$scope.photos = response;
			return;
		});
	}

	$scope.backupAlbum = function (val) {
		$scope.album_id = val;
		$http({
			method: 'GET',
			url: '/backup?album_id=' + $scope.album_id
		}).success(function (response) {
			$scope.status = response;
			if ($scope.status === null) {
				alert("Album " + $scope.status._id + " is successfully backed up!");
			} else {
				alert("Sorry, Backup failed");
			}
			return status;
		});
	}

	$scope.getAlbumMeta = function () {
		$scope.detailDispVal = 'true';
	}


});
>>>>>>> origin/master
