import React from 'react';

import Request from './request';
import Response from './response';

class Main extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      res: {
        status: '',
        body: ''
      }
    };
  }

  handleSend(req) {
    $.post('/http/send', JSON.stringify(req))
      .done(res => {
        this.setState({res: res});
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
          <Response status={this.state.res.status} body={this.state.res.body} />
        </div>
      </div>
    );
  }
}

export default Main;