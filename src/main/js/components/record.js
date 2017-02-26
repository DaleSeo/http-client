import React from 'react';

class Record extends React.Component {

  render() {
    let className = this.getClassname(this.props.method);
    return (
      <button type="button" class="list-group-item">
        <span class={className}>{this.props.method}</span> {this.props.url}
      </button>
    );
  };

  getClassname(method) {
    let className = 'label ';
    switch (method) {
      case 'GET':
        className += 'label-success';
        break;
      case 'POST':
        className += 'label-info';
        break;
      case 'PUT':
        className += 'label-warning';
        break;
      case 'DELETE':
        className += 'label-danger';
        break;
      default:
        className += 'label-primary';
    }
    return className;
  }

}

export default Record;
