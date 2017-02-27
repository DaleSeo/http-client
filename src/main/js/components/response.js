import React from 'react';

class Response extends React.Component {
  render() {
    let className = 'alert ';
    if (this.props.status.substr(0, 1) === '2') {
      className += 'alert-success';
    } else if (this.props.status.substr(0, 1) === '4') {
      className += 'alert-warning';
    } else if (this.props.status.substr(0, 1) === '5') {
      className += 'alert-danger';
    } else {
      className += 'alert-info';
    }

    return (
      <div id="response">
        <h2>Response</h2>
        <div class={className}>
          {this.props.status}
        </div>
        <div class="well">
          {this.props.body}
        </div>
      </div>
    );
  };
}

export default Response;
