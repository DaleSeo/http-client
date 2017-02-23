import React from 'react';

import Request from './request';
import Response from './response';

class Main extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      res: JSON.stringify({})
    };
  }

  handleSend(req) {
    $.post('/http/send', JSON.stringify(req))
      .done(res => {
        this.setState({res: JSON.stringify(res)});
      });
  }

  render() {
    return (
      <div class="container">
        <div class="page-header">
          <h1>Http Client <small>ver 0.0.1</small></h1>
        </div>
        <div class="row">
          <Request onSend={this.handleSend.bind(this)} />
        </div>
        <div class="row">
          <Response res={this.state.res} />
        </div>
      </div>
    );
  }
}

export default Main;