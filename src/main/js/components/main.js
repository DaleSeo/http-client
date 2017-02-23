import React from 'react';

import Response from './response';

class Main extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      res: JSON.stringify({})
    };

    this.handleSend = this.handleSend.bind(this);
  }

  handleSend(event) {
    $.get('http://localhost:8080/rest/send')
      .done(data => {
        this.setState({res: JSON.stringify(data)});
      });
  }

  render() {
    return (
      <div class="container">
        <div class="page-header">
          <h1>Http Client <small>ver 0.0.1</small></h1>
        </div>
        <div class="row">
          <h2>Request</h2>
          <form>
            <div class="form-group">
              <label for="path">Path</label>
              <input type="text" class="form-control" id="path" placeholder="Path"/>
            </div>
            <button type="button" class="btn btn-default" onClick={this.handleSend}>Send</button>
          </form>
        </div>
        <div class="row">
          <Response res={this.state.res}/>
        </div>
      </div>
    );
  }
}

export default Main;