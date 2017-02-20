import React from 'react';

export default class Response extends React.Component {
  render() {
    return (
      <div class="well">
        {this.props.body}
      </div>
    );
  };
}