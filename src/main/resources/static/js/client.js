function logout() {
    $("#main").hide();
    $("#form").show();
    Cookies.remove("token");
    Cookies.remove("username");
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function insert_movie(movie, list, initial_rating) {
    $("#" + list).append('<li><h3>' + movie.title + '</h3><h4>' +  movie.genre + '</h4>' +
        '<div class = "bar" id="bar-' + list + movie.id + '"></div>' +
        '<span id = "rating-' + list + movie.id + '"></span></li>');

    $("#bar-" + list + movie.id).rateYo({
        numStars: 10,
        fullStar: true,
        starWidth: "20px",
        rating: initial_rating / 2,
        onChange: function (rating, rateYoInstance) {
            if (rating > 0) {
                $("#rating-" + list + movie.id).text("Your rating: " + rating  * 2 + "/10");
            } else {
                $("#rating-" + list + movie.id).text("");
            }
        },
        onSet: function(rating, rateYoInstance) {
            rate_movie(movie.id, rating * 2);
        }
    });

    if (initial_rating > 0) {
        $("#rating-" + list + movie.id).text("Your rating: " + initial_rating + "/10");
    }
}

function rate_movie(id, rating) {
    $.ajax({
        url: 'http://localhost:8080/users/' + Cookies.get("username"),
        type : "POST",
        contentType: 'application/json',
        data : '{"username":"' + Cookies.get("username") + '", "movieId":"' + id + '", "newRating":"' + rating + '"}',
        headers: {"Authorization": Cookies.get("token")},
        dataType: 'text',
        mimeType: 'text/html',
        success : function(result, status, xhr) {
            refresh_recommendations();
            refresh_ratings();
        },
        error : function(data) {
        }
    });
}

function update_rating(rating, movie, list) {
    $("#bar-" + list + movie.id).rateYo("rating", rating / 2);
}

function refresh_ratings() {
    console.log("rating");
    $.ajax({
        url: "http://localhost:8080/users/" + Cookies.get("username"),
        type : "GET",
        headers: {"Authorization": Cookies.get("token")}
    }).then(function(data) {
        $("#rating_list").text("");
        $.each(data.userRatings, function(index, val) {
            insert_movie(val.movie, "rating_list", val.rating);
            //update_rating(val.rating, val.movie, "rating_list");
        });
    });
}

function refresh_recommendations() {
    console.log("recommendations");
    $.ajax({
        url: "http://localhost:8080/recommendations/" + Cookies.get("username"),
        type : "GET",
        headers: {"Authorization": Cookies.get("token")}
    }).then(function(data) {
        $("#recommended_movies").text("");
        $.each(data, function(index, val) {
            insert_movie(val, "recommended_movies", 0);
        });
    });
}

function refresh_search_results() {
    $.ajax({
        url : "http://localhost:8080/movies/find/" + $("#title").val(),
        type : "GET",
        headers: {"Authorization": Cookies.get("token")}
    }).then(function(data) {
        $("#search_result").text("");
        $.each(data, function(index, val) {
            insert_movie(val, "search_result", 0);
        });
    });
}

$(document).ready(function(){
    if (typeof Cookies.get("token") != 'undefined') {
        refresh_recommendations();
        refresh_ratings();
        $("#logged_user").text(Cookies.get("username"));
        $("#main").show();
    } else {
        $("#form").show();
    }

    $("#logout").on('click', logout);
    $("#title").on('change', refresh_search_results);

    $("#login_button").on('click', function(){
        $.ajax({
            url: 'http://localhost:8080/login',
            type : "POST",
            contentType: 'application/json',
            data : JSON.stringify(getFormData($("#login_form"))),
            dataType: 'text',
            mimeType: 'text/html',
            success : function(result, status, xhr) {
                Cookies.set("username", $("#username_form").val(), { expires : 10 });
                Cookies.set("token", xhr.getResponseHeader("Authorization"), { expires : 10 });
                console.log(Cookies.get("token"));
                refresh_recommendations();
                refresh_ratings();
                $("#logged_user").text(Cookies.get("username"));
                $("#main").show();
                $("#form").hide();
            },
            error : function(data) {
                $("#login_result").text("Invalid credentials, try again");
            }
        });
    });

    $("#sign_button").on('click', function(){
        $.ajax({
            url: 'http://localhost:8080/users/register',
            type : "POST",
            contentType: 'application/json',
            data : JSON.stringify(getFormData($("#signup_form"))),
            dataType: 'text',
            mimeType: 'text/html',
            statusCode: {
                400: function (response) {
                    $("#signup_result").text("Invalid data");
                },
                409: function (response) {
                    $("#signup_result").text("User Already exists");
                }
            },
            success : function(result, status, xhr) {
                $("#signup_result").text("Signed up, please login to use the system");
            },
        });
    });
});