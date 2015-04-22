var app = angular.module('myApp', []);
app.controller('myCtrl', ['$scope', '$http', function ($scope, $http) {

	$scope.dispVal = 'true';
	$scope.detailDispVal = 'true';
	$scope.showModal = 'true';

	$scope.getAlbums = function () {
		$scope.dispVal = !$scope.dispVal;
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
			if ($scope.status !== null) {
				alert("Album " + $scope.status._id + " is successfully backed up!");
			} else {
				alert("Sorry, Backup failed");
			}
			return status;
		});
	}

	$scope.getAlbumMeta = function () {
		$scope.detailDispVal = !$scope.detailDispVal;
	}

	$scope.showPhotos = function (val) {
		$scope.showModal = !$scope.showModal;
		$scope.status = val + 'Coming Soon';
		$scope.album_id = val;
        $http({
            method: 'GET',
            url: '/' + $scope.album_id + '/photos'
        }).success(function (photos) {
            $scope.photo_id = photos.id;
            $scoope.photo_location = photos.source;
            return;
        });
	}

}]);
