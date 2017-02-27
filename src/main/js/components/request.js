import React from 'react';

export default class Response extends React.Component {

  handleSend(event) {
    let req = {url: {}};
    req.method = $('#method').val() || 'GET';
    req.url.path = $('#path').val() || 'http://jsonplaceholder.typicode.com/posts/1';
    req.body = $('#body').val();
    this.props.onSend(req);
  }

  render() {
    return (
      <div id="request">
        <h2>Request</h2>
        <form id="formReq">
          <div class="form-group">
            <label for="method">Method</label>
            <select id="method" name="method" class="form-control">
              <option>GET</option>
              <option>POST</option>
              <option>PUT</option>
              <option>DELETE</option>
            </select>
          </div>
          <div class="form-group">
            <label for="path">Path</label>
            <input id="path" name="path" type="text" class="form-control"/>
          </div>
          <div class="form-group">
            <label for="body">Body</label>
            <textarea id="body" name="body" class="form-control" rows="3"></textarea>
          </div>
          <div class="text-right">
            <div class="btn-group">
              <button type="button" class="btn btn-primary" onClick={this.handleSend.bind(this)}>Send</button>
              <button type="reset" class="btn btn-default">Reset</button>
            </div>
          </div>
        </form>
      </div>
    );
  };
}