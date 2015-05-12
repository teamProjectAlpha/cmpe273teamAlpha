var app = angular.module('myApp', ['akoenig.deckgrid', 'ui.bootstrap']);

app.controller('myCtrl', ['$scope', '$http', function ($scope, $http) {
    //alert("I am in controller");
	$scope.dispVal = 'true';
	$scope.detailDispVal = 'true';
	$scope.showModal = 'true';
    $scope.albumFirstPhotos = [];

    /*we will fetch link for the first photo of the given album*/
    $scope.albumPhotoLinks = function(albumid){

    };

	$scope.getAlbums = function (src) {
        $scope.srcSelected = src;
        if($scope.srcSelected === 'facebook') {

            $http({
                method: 'GET',
                url: '/getalbums'
            }).success(function (response) {
                $scope.albums = response;
                return response;
            })

        } else if($scope.srcSelected === 'backup'){
            /* Step 1: Get meta from Mongo*/
            /*Step 2: Get Photo Links from S3*/
            /*Step3: Combine Objects and return*/
            //console.log("I am in backup");
            $http({
                method: 'GET',
                url: '/getbackedupalbums'
            }).success(function (response) {
                $scope.albums = response;
                return response;
            })
        }
	};

	$scope.getPhotos = function (val) {
		$scope.album_id = val;
		$http({
			method: 'GET',
			url: '/' + $scope.album_id + '/photos'
		}).success(function (response) {
			$scope.photos = response;
			return;
		});
	};

    $scope.backupAlbum = function (val) {
            alert("Started backup... This may take few seconds to complete...Click OK to continue. id: " + val);
            $scope.album_id = val;

            $http({
			method: 'GET',
			url: '/backup?album_id=' + $scope.album_id
		}).success(function (response) {
			$scope.album_id = response;
			if ($scope.album_id !== null) {
				alert("Album " + $scope.album_id + " is successfully backed up!");
			} else {
				alert("Sorry, Backup failed");
			}

			return;
		});
	};

	$scope.getAlbumMeta = function (val) {
    //function getAlbumMeta() {
        //alert("please work"+val);
        console.log("I am in get albummeta" + val);
		$scope.album_id = val;
		$scope.detailDispVal = !$scope.detailDispVal;
		$scope.isBackedUp = 'false';

		$http({
			method: 'GET',
			url: '/getalbummeta?album_id=' + $scope.album_id
		}).success(function (album) {
			$scope.mongoAlbum = album;
		});

		if ($scope.isBackedUp !== null) {
			return;
		} else {
			alert("Album not found on MongoDB!");
			return;
		}
	};

    $scope.imageUrl = function (albumId,photoId) {

        $http({
            method: 'GET',
            url: '/'+albumId+'/photos/'+photoId
        }).success(function (response) {

            if (response !== null) {

                return response;
            }

        });
    };

    $scope.delete = function (albumId) {

        document.getElementById(albumId).hidden=true;
        $http({
            method: 'DELETE',
            url: '/delete?album_id='+albumId
        }).success(function (response) {
            if (response !== null) {
                return response;
            }else{
                return null;
            }

        });
    };

    $scope.getPhotoMeta = function (val) {

        $scope.selectedPhoto=val;
    };


	$scope.showPhotos = function (val) {

		$scope.album_id = val;
		$scope.isBackedUp = 'false';

		$http({
			method: 'GET',
			url: '/getalbummeta?album_id=' + $scope.album_id

		}).success(function (response) {
			$scope.status = response;
			if (status !== null) {
				$scope.isBackedUp = 'true';
			}

		});

		if ($scope.isBackedUp) {
			$http({
				method: 'GET',
				url: '/' + $scope.album_id + '/photos'
			}).success(function (photos) {

				$scope.photos = photos;
				return;
			});
		} else {
			alert('Please backup this album first!');
		}
	}

}]);
