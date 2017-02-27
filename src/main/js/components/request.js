import React from 'react';

class Request extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      path: '',
      body: ''
    };
  }

  setPath(event) {
    this.setState({
      path: event.target.value
    });
  }

  setBody(event) {
    this.setState({
      body: event.target.value
    });
  }

  checkState() {
    this.setState({
      path: 'http://jsonplaceholder.typicode.com/posts',
      body: '{}'
    });
    console.log('path:', this.state.path);
    console.log('body:', this.state.body);
  }

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
            <input id="path" name="path" type="text" class="form-control" value={this.state.path} onChange={this.setPath.bind(this)} />
          </div>
          <div class="form-group">
            <label for="body">Body</label>
            <textarea id="body" name="body" class="form-control" rows="3" value={this.state.body} onChange={this.setBody.bind(this)} />
          </div>
          <div class="text-right">
            <div class="btn-group">
              <button type="button" class="btn btn-primary" onClick={this.handleSend.bind(this)}>Send</button>
              <button type="reset" class="btn btn-default">Reset</button>
              <button type="button" class="btn btn-warning" onClick={this.checkState.bind(this)}>Check</button>
            </div>
          </div>
        </form>
      </div>
    );
  };
}

export default Request;