$.fn.serializeObject = function() {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function() {
    if (o[this.name] !== undefined) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};

const BASE_URL = 'http://dale-http.herokuapp.com';

$.ajaxSetup({
  contentType: "application/json; charset=UTF-8",
  beforeSend: function(xhr, options) {
    options.url = BASE_URL + options.url;
  }
});
